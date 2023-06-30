package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.view.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.model.view.*;
import it.polimi.ingsw.utils.CommandReader;
import it.polimi.ingsw.utils.OptionsValues;
import it.polimi.ingsw.view.*;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

/**
 * This class represents the {@code TextualUI}, an extension of the Game's UI
 * that contains methods used to react to the player's interactions in the CLI textual format.
 * These methods are related to the management of the first user's interaction at the start of the game, that of
 * the waiting states for the players lobby, the chat, line selection and tile's management and many others.
 * It also provides the displaying of messages to require a particular input necessary to proceed from a turn to the following one.
 *
 * @see UI
 * @see it.polimi.ingsw.model.Game
 * @see it.polimi.ingsw.model.Player
 */
public class TextualUI implements UI {

    private final GenericUILogic genericUILogic;

    private TUIListener countdownListener;

    /**
     * Class constructor.
     * Initialize the game's model.
     *
     * @param genericUILogic the logic associated to the given TextualUI.
     * @see GenericUILogic
     */
    public TextualUI(GenericUILogic genericUILogic) {
        this.genericUILogic = genericUILogic;
    }

    /**
     * Class constructor.
     * Initialize the game's model.
     */
    public TextualUI() {
        this.genericUILogic = new GenericUILogic();
        TUIListener countdownHandlerThread = new CountdownHandler(genericUILogic);
        this.registerCountdownListener(countdownHandlerThread);
        countdownHandlerThread.start();
    }

    /**
     * Register the listener of the CountdownHandler
     *
     * @param listener the listener
     */
    public void registerCountdownListener(TUIListener listener) {
        this.countdownListener = listener;
    }

    /**
     * Remove the listener of the CountdownHandler
     */
    public void removeCountdownListener() {
        this.countdownListener = null;
    }

    /**
     * Prints the standard message when the game starts, to welcome the players and ask for their nicknames.
     * Reiterates until all the players' nicknames have been acquired.
     * Identifies the first player and the total number of players in the lobby.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     */
    private void firstInteractionWithUser() {
        this.printTitleScreen();
        System.out.println("Welcome to My Shelfie");

        this.genericUILogic.initializeChatThread(this.genericUILogic.getController(), this.genericUILogic.getNickname(), this.genericUILogic.getModel());

        do {
            this.genericUILogic.setExceptionToHandle(null);
            System.out.println("Insert your nickname!");
            String nickname = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();

            this.setNickname(nickname);
            this.genericUILogic.getController().addPlayer(this.genericUILogic.getNickname());

            if (this.genericUILogic.getExceptionToHandle() != null) {
                this.genericUILogic.getExceptionToHandle().handle();
            }

        } while (this.genericUILogic.getExceptionToHandle() != null);

        this.countdownListener.noExceptionOccured();

        this.genericUILogic.getController().areThereStoredGamesForPlayer(this.genericUILogic.getNickname());

        if (this.genericUILogic.areThereStoredGamesForPlayer() && genericUILogic.getModel().getPlayers().size() == 1) {
            String restoreGameChoice;
            do {
                System.out.println("There is a stored game for your user. Would you like to restore it? (Type \"YES\" to restore it, \"NO\" to delete it)");
                restoreGameChoice = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            } while (!restoreGameChoice.equalsIgnoreCase("YES") && !restoreGameChoice.equalsIgnoreCase("NO"));

            if (restoreGameChoice.equalsIgnoreCase("YES")) {
                this.genericUILogic.getController().restoreGameForPlayer(this.genericUILogic.getNickname());
                System.out.println("Stored game has been restored correctly");

            } else if (restoreGameChoice.equalsIgnoreCase("NO")) {
                this.setUpLobbyAsFirst();
            }
        } else {
            if (this.genericUILogic.getModel().getPlayers().size() == 1) {
                this.setUpLobbyAsFirst();
            } else {
                this.setUpLobby();
            }
        }
        System.out.println(this.genericUILogic.getState());
        waitWhileInState(ClientGameState.WAITING_IN_LOBBY);
    }

    /**
     * Implement the wait of the view when in a certain state
     *
     * @param clientGameState a state in which the client must wait
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.model.Player
     */
    private void waitWhileInState(ClientGameState clientGameState) {
        synchronized (this.genericUILogic.getLockState()) {
            switch (clientGameState) {
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting for the game to start...");
                }
                case WAITING_FOR_OTHER_PLAYER ->
                        System.out.println("Waiting for others player moves: " + this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getNickname() + "...");
            }
            while (genericUILogic.getState() == clientGameState) {
                try {
                    genericUILogic.getLockState().wait();


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * EImplement the wait of the view when in a certain state
     *
     * @param gameStates a set of state in which the client must wait
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.model.Player
     */
    private void waitWhileInStates(List<ClientGameState> gameStates) {
        int precActivePlayerIndex = -1;
        synchronized (this.genericUILogic.getLockState()) {
            switch (genericUILogic.getState()) {
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting for the game to start...");
                }
            }
            while (gameStates.contains(genericUILogic.getState())) {
                try {
                    if (genericUILogic.getState() == ClientGameState.WAITING_FOR_OTHER_PLAYER) {
                        if (precActivePlayerIndex != genericUILogic.getModel().getActivePlayerIndex()) {
                            System.out.println("Waiting for others player moves: " + this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getNickname() + "...");
                            precActivePlayerIndex = genericUILogic.getModel().getActivePlayerIndex();
                        }
                    }
                    genericUILogic.getLockState().wait();


                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Register the listener that TUI notify
     *
     * @param listener the listener that will receive the notification of the TUI.
     */
    @Override
    public void registerListener(ViewListener listener) {
        this.genericUILogic.registerListener(listener);
    }

    /**
     * Remove the listener that TUI notify
     */
    @Override
    public void removeListener() {
        this.genericUILogic.removeListener();
    }

    /**
     * Set the nickname of the player associated with the TUI
     *
     * @param nickname the player's nickname.
     */
    @Override
    public void setNickname(String nickname) {
        this.genericUILogic.setNickname(nickname);
    }

    /**
     * Delegates its implementation to the {@code GenericUILogic}
     *
     * @param modelUpdated the updated game's model.
     * @see GenericUILogic#modelModified(GameView)
     */
    @Override
    public void modelModified(GameView modelUpdated) {
        this.genericUILogic.modelModified(modelUpdated);
    }

    /**
     * Delegates its implementation to the {@code GenericUILogic}
     *
     * @param exception the given GenericException.
     * @see GenericUILogic#printException(GenericException)
     */
    @Override
    public void printException(GenericException exception) {
        this.genericUILogic.printException(exception);
    }

    /**
     * Delegates its implementation to the {@code GenericUILogic}
     *
     * @param result {@code true} if and only if the game has been stored properly, {@code false} otherwise.
     * @see GenericUILogic#setAreThereStoredGamesForPlayer(boolean)
     */
    @Override
    public void setAreThereStoredGamesForPlayer(boolean result) {
        this.genericUILogic.setAreThereStoredGamesForPlayer(result);
    }

    /**
     * Implements the game flow of the TUI
     */
    @Override
    public void run() {
        //------------------------------------ADDING PLAYER TO THE LOBBY------------------------------------
        firstInteractionWithUser();

        while (this.genericUILogic.getState() != ClientGameState.GAME_ENDED) {
            //------------------------------------WAITING OTHER PLAYERS-----------------------------------
            //waitWhileInState(ClientGameState.WAITING_FOR_OTHER_PLAYER);
            waitWhileInStates(Arrays.asList(ClientGameState.WAITING_FOR_OTHER_PLAYER, ClientGameState.WAITING_FOR_RESUME));
            if (this.genericUILogic.getState() == ClientGameState.GAME_ENDED) break;
            //------------------------------------FIRST GAME RELATED INTERACTION------------------------------------
            showNewTurnIntro();
            Choice choice = askPlayer();
            if (this.genericUILogic.getState() == ClientGameState.GAME_ENDED) break;
            //---------------------------------NOTIFY CONTROLLER---------------------------------
            this.genericUILogic.getController().insertUserInputIntoModel(choice);
            if (this.genericUILogic.getExceptionToHandle() != null) {
                this.genericUILogic.getExceptionToHandle().handle();
                this.genericUILogic.setExceptionToHandle(null);
            }
            this.genericUILogic.getController().changeTurn();
        }
        if (this.genericUILogic.getExceptionToHandle() != null) {
            this.genericUILogic.getExceptionToHandle().handle();
        } else {
            showGameEnd();
        }
    }

    /**
     * Displays a standard message to identify the starting of the next turn.
     * Calls the nickname of the active player and the shows the board's state.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     */
    @Override
    public void showNewTurnIntro() {
        System.out.println("---NEW TURN---");
        String activePlayerNickname = this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getNickname();
        System.out.println("It's your turn: " + activePlayerNickname + "!");
        System.out.println("Current board state:");
        System.out.println(this.genericUILogic.getModel().getBoard());
    }

    /**
     * Identifies the choices of the player during the turn, the ones related to tiles selection.
     *
     * @param iterationCount   used to iterate on the player's choice (the calls for input insertion in a turn are limited).
     * @param isRowBeingChosen {@code true} if and only if the selected bookshelf's row is the one passed as parameter, {@code false} otherwise.
     * @return the index of the chosen rowColumnTile.
     * @see it.polimi.ingsw.model.Player
     * @see Choice
     * @see it.polimi.ingsw.model.Bookshelf
     * @see it.polimi.ingsw.model.tile.Tile
     */
    private int rowColumnTileChoiceFromBoard(int iterationCount, boolean isRowBeingChosen) {
        boolean isInsertCorrect = false;
        int choice = 0;
        while (!isInsertCorrect) {
            System.out.println("Insert the " + (isRowBeingChosen ? "row" : "column") + " of the " + (iterationCount + 1) + "^ tile you want to take:");
            try {
                choice = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
            } catch (InputMismatchException e) {
                System.err.println("A not valid value has been entered, try again!");
                CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            }

            if (choice > 0 && choice <= (isRowBeingChosen ? this.genericUILogic.getModel().getBoard().getNumberOfRows() : this.genericUILogic.getModel().getBoard().getNumberOfColumns())) {
                isInsertCorrect = true;
            } else {
                System.err.println("Insert a valid " + (isRowBeingChosen ? "row" : "column") + " (A number between 1 and " + (isRowBeingChosen ? this.genericUILogic.getModel().getBoard().getNumberOfRows() : this.genericUILogic.getModel().getBoard().getNumberOfColumns()) + "!)");
            }
        }
        return choice;
    }

    /**
     * Identifies the player's chosen column in the bookshelf.
     *
     * @param iterationCount used to iterate on the player's choice (the calls for input insertion in a turn are limited).
     * @return the index of the selected column.
     * @see it.polimi.ingsw.model.Bookshelf
     * @see it.polimi.ingsw.model.Player
     * @see Choice
     */
    private int bookshelfColumnChoice(int iterationCount) {
        boolean isInsertCorrect;
        int chosenColumn = 0;
        do {
            isInsertCorrect = true;
            System.out.println("Choose the column in which you want to insert the tiles:");
            try {
                chosenColumn = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
                if (chosenColumn <= 0 || chosenColumn > this.genericUILogic.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns()) {
                    isInsertCorrect = false;
                    System.err.println("You have choosen a column out of bookshelf's bounds. Insert a value between" +
                            " 1 and " + this.genericUILogic.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns() + "!");
                } else {
                    if (this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getBookshelf().getNumberOfEmptyCellsInColumn(chosenColumn - 1) < iterationCount) {
                        isInsertCorrect = false;
                        System.err.println("You have choosen a column with not enough empty spaces to insert the chosen tiles, try again!");
                    }
                }
            } catch (InputMismatchException ignored) {
                isInsertCorrect = false;
                System.err.println("A not valid value has been entered, try again!");
                CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            }
        } while (!isInsertCorrect);
        return chosenColumn;
    }

    /**
     * Displays the part of the CLI interacting with the user to ask the type of action
     * the player will enact after the method's call.
     * In order to do this it interacts with the commands queue.
     * The possible choices are: 'display the personal recap', tile's selection, 'send chat message', call disconnection.
     *
     * @return the player's choice.
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.network.Client
     * @see it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer.DisconnectPlayerCommand
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.utils.CommandQueue
     * @see it.polimi.ingsw.utils.CommandReader
     */
    public Choice askPlayer() {
        while (true) {
            System.out.println("Choose what to do (Type the number paired to the action):");
            System.out.println("1)Recap personal state");
            System.out.println("2)Choose tiles");
            System.out.println("3)Disconnect");
            String input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            switch (input) {
                case "1" -> {
                    showPersonalRecap();
                }
                case "2" -> {
                    System.out.println("Current board state:");
                    System.out.println(this.genericUILogic.getModel().getBoard());

                    int counter = 0, firstRow = 0, firstColumn = 0;

                    Choice playerChoice = new Choice();
                    Direction directionToCheck = null;

                    //---------------------------------TILES COORDINATES CHOICE---------------------------------
                    int maxNumberOfCellsFreeInBookshelf = this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getBookshelf().getMaxNumberOfCellsFreeInBookshelf();
                    do {
                        int row, column;
                        row = rowColumnTileChoiceFromBoard(counter, true);
                        column = rowColumnTileChoiceFromBoard(counter, false);

                        if (checkIfPickable(row - 1, column - 1)) {
                            switch (counter) {
                                case 0 -> {
                                    counter++;
                                    firstRow = row;
                                    firstColumn = column;
                                    playerChoice.addTile(this.genericUILogic.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                    playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                }
                                case 1 -> {
                                    Direction res = checkIfInLine(row, column, firstRow, firstColumn);
                                    if (res != null) {
                                        directionToCheck = res;
                                        counter++;
                                        playerChoice.addTile(this.genericUILogic.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                    }
                                }
                                case 2 -> {
                                    if (checkIfInLine(row - 1, column - 1, playerChoice.getTileCoordinates(), directionToCheck)) {
                                        counter++;
                                        playerChoice.addTile(this.genericUILogic.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                    }
                                }
                                default -> {
                                    System.err.println("[INPUT:ERROR]: Unexpected number of chosen tiles, found: " + counter + ", expected value < 3");
                                }
                            }
                        }
                        if (counter < maxNumberOfCellsFreeInBookshelf) {
                            if (counter > 0 && counter != OptionsValues.MAX_NUMBER_PICKABLE_TILES) {
                                do {
                                    System.out.println("Do you want to pick another tile? (Type \"YES\" to continue, \"NO\" to stop)");
                                    input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
                                } while (!input.equalsIgnoreCase("YES") && !input.equalsIgnoreCase("NO"));
                            }
                        } else {
                            input = "NO";
                        }
                    } while (!input.equalsIgnoreCase("NO") && counter < 3);

                    //---------------------------------COLUMN CHOICE---------------------------------
                    System.out.println(this.genericUILogic.getModel().getPlayers().get(this.genericUILogic.getModel().getActivePlayerIndex()).getBookshelf());

                    int chosenColumn = bookshelfColumnChoice(counter);

                    playerChoice.setChosenColumn(chosenColumn - 1);
                    //---------------------------------ORDER CHOICE---------------------------------

                    if (counter == 1) {
                        playerChoice.setTileOrder(new int[]{0});
                    } else {
                        System.out.println("Choose the tiles' insertion order(1 stands for the first picked tile, 2 for the second and 3 for the third.");
                        System.out.println("An example of insertion is: (1,3,2)");
                        boolean isInsertCorrect = false;
                        do {
                            input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
                            String[] temp;
                            temp = input.split(",");
                            boolean res = false;
                            if (temp.length == counter) {
                                switch (counter) {
                                    case 2 -> {
                                        res = Arrays.asList(temp).containsAll(Arrays.asList("1", "2"));
                                    }
                                    case 3 -> {
                                        res = Arrays.asList(temp).containsAll(Arrays.asList("1", "2", "3"));
                                    }
                                    default -> {
                                        System.err.println("[INPUT:ERROR] Unexpected value of chosen tiles");
                                    }
                                }

                                if (res) {
                                    int[] chosenPositions = new int[temp.length];
                                    for (int i = 0; i < temp.length; i++) {
                                        chosenPositions[i] = Integer.parseInt(temp[i]) - 1;
                                    }
                                    playerChoice.setTileOrder(chosenPositions);
                                    isInsertCorrect = true;
                                } else {
                                    System.err.println("Not fitting digits have been entered");
                                }
                            } else {
                                System.err.println("The number of digits that has been entered is different from the " +
                                        "number of chosen tiles or the given input does not meet the given format, try again!");
                            }
                        } while (!isInsertCorrect);
                    }

                    return playerChoice;
                }
                case "3" -> {
                    this.genericUILogic.getController().disconnectPlayer(this.genericUILogic.getNickname());
                    System.err.println("You disconnected from the game");
                    System.exit(0);
                }
                default -> {
                    System.err.println("You have not entered a valid value, please try again! (Enter one of the menu indexes)");
                }
            }
        }
    }

    /**
     * Checks if the first two tiles chosen by the {@code Player} are present in the same direction.
     * The HORIZONTAL label is referred to the presence of the second {@code Tile}
     * of the chosen set from in row.
     * The VERTICAL label is referred to the presence of the second {@code Tile}
     * of the chosen set from in column.
     * Verify if the {@code Player} commit mistakes during selection.
     *
     * @param row         the row in selection.
     * @param column      the column in selection.
     * @param firstRow    the inspection starting row.
     * @param firstColumn the inspection starting column.
     * @return the direction of the two current tiles chosen.
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.tile.Tile
     */
    private Direction checkIfInLine(int row, int column, int firstRow, int firstColumn) {
        if (row == firstRow && column == firstColumn) {
            System.err.println("You cannot choose a tile that has already been chosen, try again!");
            return null;
        }
        if ((row == firstRow) && (column - 1 == firstColumn || column + 1 == firstColumn)) {
            return Direction.HORIZONTAL;
        }
        if ((column == firstColumn) && (row - 1 == firstRow || row + 1 == firstRow)) {
            return Direction.VERTICAL;
        }
        System.err.println("Selected tiles must form a straight line and be adjacent, try again!");
        return null;
    }

    /**
     * Check if the {@code Tile} chosen by the {@code Player} is already
     * present in the current selection at the given coordinates and
     * if the {@code Player} has properly inserted all the tiles in the {@code Board}.
     *
     * @param row                  the row in selection.
     * @param column               the column in selection.
     * @param prevTilesCoordinates the coordinates of the Tiles which have already been placed on the {@code Board}.
     * @param directionToCheck     the direction (on row/column) which is selected for inspection.
     * @return {@code true} if and only if the {@code Tile}
     * has already been placed in a previous turn.
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     */
    private boolean checkIfInLine(int row, int column, List<Coordinates> prevTilesCoordinates, Direction directionToCheck) {
        if (prevTilesCoordinates.contains(new Coordinates(row, column))) {
            System.err.println("You cannot choose a tile that has already been chosen, try again!");
            return false;
        }
        switch (directionToCheck) {
            case HORIZONTAL -> {
                if (row != prevTilesCoordinates.get(0).getX()) {
                    System.err.println("Selected tiles must form a straight line and be adjacent, try again!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getY() == column + 1 || coordinates.getY() == column - 1) {
                            return true;
                        }
                    }
                    System.err.println("Selected tiles must form a straight line and be adjacent, try again!");
                }
                return false;
            }
            case VERTICAL -> {
                if (column != prevTilesCoordinates.get(0).getY()) {
                    System.err.println("Selected tiles must form a straight line and be adjacent, try again!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getX() == row + 1 || coordinates.getX() == row - 1) {
                            return true;
                        }
                    }
                    System.err.println("Selected tiles must form a straight line and be adjacent, try again!");
                }
                return false;
            }
            default -> {
                System.err.println("Something went wrong, i didn't expected this value");
                return false;
            }
        }
    }

    /**
     * Method that verifies if the tiles in the turn selection are available
     * to the player for picking.
     *
     * @param row    is the index of the selected row.
     * @param column is the index of the selected column.
     * @return {@code true} if and only if the {@code boardMatrix}
     * presents a collectable tile in the position
     * identified though the given coordinates;
     * {@code false} otherwise
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.tile.Tile
     */
    private boolean checkIfPickable(int row, int column) {
        BoardView board = this.genericUILogic.getModel().getBoard();
        TileView[][] boardMatrix = board.getTiles();

        if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
            if ((row != 0 && (boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                    (row != board.getNumberOfRows() - 1 && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                    (column != board.getNumberOfColumns() - 1 && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
                    (column != 0 && (boardMatrix[row][column - 1] == null || boardMatrix[row][column - 1].getColor() == null))) {
                return true;
            } else {
                System.err.println("Impossible to take the tile (It has all sides occupied), try again!");
            }
        } else {
            System.err.println("There is no tile in the selected cell, try again!");
        }
        return false;
    }

    /**
     * Displays the recap of the player during the turn
     * with his score and personal goal, the common goals and
     * number of completed goals overall.
     * Also shows the current state of the {@code Bookshelf}.
     *
     * @see it.polimi.ingsw.model.Bookshelf
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.commongoal.CommonGoal
     * @see it.polimi.ingsw.model.PersonalGoal
     */
    public void showPersonalRecap() {
        PlayerView activePlayer = this.genericUILogic.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.genericUILogic.getNickname())).toList().get(0);
        BookshelfView playerBookshelf = activePlayer.getBookshelf();
        PersonalGoalView playerPersonalGoal = activePlayer.getPersonalGoal();
        List<ScoreTileView> playerScoreTiles = activePlayer.getScoreTiles();

        int playerScore = activePlayer.score();

        List<CommonGoalView> commonGoals = this.genericUILogic.getModel().getCommonGoals();
        List<ScoreTileView> scoreTileFirstCommonGoal = commonGoals.get(0).getScoreTiles();
        List<ScoreTileView> scoreTileSecondCommonGoal = commonGoals.get(1).getScoreTiles();

        System.out.println("Here is your recap:");
        System.out.println("Bookshelf's state:\n" + playerBookshelf + "\n" +
                "Personal goal:\n" + playerPersonalGoal + "\n" +
                "Common goals:\n" +
                commonGoals.get(0) + "Highest tile available: " + (scoreTileFirstCommonGoal.size() > 0 ? scoreTileFirstCommonGoal.get(0).getValue() : "0") + "\n\n" +
                commonGoals.get(1) + "Highest tile available: " + (scoreTileSecondCommonGoal.size() > 0 ? scoreTileSecondCommonGoal.get(0).getValue() : "0") + "\n\n" +
                "Completed common goals: First common goal:" + (playerScoreTiles.size() > 0 && playerScoreTiles.get(0) != null ? playerScoreTiles.get(0).getValue() : "/") +
                ", Second common goal:" + (playerScoreTiles.size() > 1 && playerScoreTiles.get(1) != null ? playerScoreTiles.get(1).getValue() : "/") + ", Victory:" +
                (playerScoreTiles.size() > 2 && playerScoreTiles.get(2) != null ? playerScoreTiles.get(2).getValue() : "/") + " (Score tiles values)" + "\n" +
                "Your current score: " + playerScore);
    }

    /**
     * Set up the lobby of the game, and proceed to start it
     *
     * @see ViewListener#startGame()
     */
    private void setUpLobby() {
        if (genericUILogic.getModel().getPlayers().size() == genericUILogic.getModel().getNumberOfPlayersToStartGame() && genericUILogic.getModel().getGameState() == GameState.IN_CREATION) {
            this.genericUILogic.getController().startGame();
        }
    }

    /**
     * Set up the lobby of the game,
     * ask the player (that is the first one) the number of players of the lobby,
     * and proceed to start it
     *
     * @see #askNumberOfPlayers()
     * @see ViewListener#startGame()
     */
    private void setUpLobbyAsFirst() {
        this.askNumberOfPlayers();

        if (genericUILogic.getModel().getPlayers().size() == genericUILogic.getModel().getNumberOfPlayersToStartGame() && genericUILogic.getModel().getGameState() == GameState.IN_CREATION) {
            this.genericUILogic.getController().startGame();
        }
    }

    /**
     * Ask the first player the number of players in order to start the game,
     *
     * @see ViewListener#chooseNumberOfPlayerInTheGame(int)
     */
    private void askNumberOfPlayers() {
        int chosenNumberOfPlayer = 0;
        do {
            System.out.println("You're the first player, how many people will play? (Min:2, Max:4)");
            chosenNumberOfPlayer = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
        } while (chosenNumberOfPlayer < 2 || chosenNumberOfPlayer > 4);
        this.genericUILogic.getController().chooseNumberOfPlayerInTheGame(chosenNumberOfPlayer);

    }

    /**
     * Prints the Game Title Screen.
     *
     * @see it.polimi.ingsw.model.Game
     */
    public void printTitleScreen() {
        new PrintStream(System.out, true, System.console() != null
                ? System.console().charset()
                : Charset.defaultCharset()
        ).println("""
                    ███╗░░░███╗██╗░░░██╗░░░░░░░░░██████╗██╗░░██╗███████╗██╗░░░░░███████╗██╗███████╗
                    ████╗░████║╚██╗░██╔╝░░░░░░░░██╔════╝██║░░██║██╔════╝██║░░░░░██╔════╝██║██╔════╝
                    ██╔████╔██║░╚████╔╝░░░░░░░░░╚█████╗░███████║█████╗░░██║░░░░░█████╗░░██║█████╗░░
                    ██║╚██╔╝██║░░╚██╔╝░░░░░░░░░░░╚═══██╗██╔══██║██╔══╝░░██║░░░░░██╔══╝░░██║██╔══╝░░
                    ██║░╚═╝░██║░░░██║░░░░░░░░░░░██████╔╝██║░░██║███████╗███████╗██║░░░░░██║███████╗
                    ╚═╝░░░░░╚═╝░░░╚═╝░░░░░░░░░░░╚═════╝░╚═╝░░╚═╝╚══════╝╚══════╝╚═╝░░░░░╚═╝╚══════╝
                """);
    }

    /**
     * Show the scoreboard of the game, sorting the player in
     * descending order of their score
     */
    public void showGameEnd() {
        List<PlayerView> playerOrderedByPoints = this.genericUILogic.getModel().getPlayers().stream().sorted((p1, p2) -> p2.score() - p1.score()).toList();
        System.out.println("""
                ---GAME ENDED---
                SCOREBOARD:
                """);
        for (int playerPosition = 0; playerPosition < playerOrderedByPoints.size(); playerPosition++) {
            System.out.println((playerPosition + 1) + ")" + playerOrderedByPoints.get(playerPosition).getNickname() + ": " + playerOrderedByPoints.get(playerPosition).score());
        }
    }
}
