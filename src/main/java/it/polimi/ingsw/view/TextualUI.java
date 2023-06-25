package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Coordinates;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.view.*;
import it.polimi.ingsw.utils.CommandReader;
import javafx.stage.Stage;
import org.fusesource.jansi.Ansi;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

import static org.fusesource.jansi.Ansi.ansi;

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
public class TextualUI extends UI {

    /**
     * Class constructor.
     * Initialize the game's model.
     *
     * @param model the assigned model.
     *
     * @see GameView
     */
    public TextualUI(GameView model) {
        super(model);
    }

    /**
     * Class constructor.
     * Initialize the game's model.
     *
     * @see GameView
     */
    public TextualUI() {
        super();
    }

    /**
     * Signals the starting of the primary stage (used for the GUI)
     *
     * @param primaryStage the GUI's main stage.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

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
        System.out.println("Benvenuto a MyShelfie, inserisci il tuo nickname!");

        this.initializeChatThread(this.controller, this.getNickname(), this.getModel());

        do {
            this.setExceptionToHandle(null);
            System.out.println("Inserisci il tuo nickname!");
            String nickname = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();

            this.setNickname(nickname);
            this.controller.addPlayer(this.getNickname());

            if (this.getExceptionToHandle() != null) {
                this.getExceptionToHandle().handle();
            }

        } while (this.getExceptionToHandle() != null);

        int chosenNumberOfPlayer = 0;
        if (getModel().getPlayers().size() == 1) {
            do {
                System.out.println("Sei il primo giocatore, per quante persone vuoi creare la lobby? (Min:2, Max:4)");
                chosenNumberOfPlayer = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
            } while (chosenNumberOfPlayer < 2 || chosenNumberOfPlayer > 4);
            this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayer);
        }

        if (getModel().getPlayers().size() == getModel().getNumberOfPlayers() && getModel().getGameState()==GameState.IN_CREATION) {
            this.controller.startGame();
        }

        waitWhileInState(ClientGameState.WAITING_IN_LOBBY);
    }

    /**
     * Evaluates the waiting states for the game's lobby and the adding of a player.
     *
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.model.Player
     *
     * @param clientGameState
     */
    private void waitWhileInState(ClientGameState clientGameState) {
        synchronized (this.getLockState()) {
            switch (clientGameState) {
                case WAITING_IN_LOBBY -> {
                    System.out.println("Waiting...");
                }
                case WAITING_FOR_OTHER_PLAYER -> System.out.println("Waiting for others player moves...");
            }
            while (getState() == clientGameState) {
                try {
                    getLockState().wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Performs the following in game actions. <p>
     * Adds the first player to the game's lobby. <p>
     * Waits for other players.<p>
     * Enacts the first player interaction.<p>
     * Notifies the controller.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public void run() {
        //------------------------------------ADDING PLAYER TO THE LOBBY------------------------------------
        firstInteractionWithUser();

        while (this.getState() != ClientGameState.GAME_ENDED) {
            //------------------------------------WAITING OTHER PLAYERS-----------------------------------
            waitWhileInState(ClientGameState.WAITING_FOR_OTHER_PLAYER);
            if (this.getState() == ClientGameState.GAME_ENDED) break;
            //------------------------------------FIRST GAME RELATED INTERACTION------------------------------------
            showNewTurnIntro();
            Choice choice = askPlayer();
            //---------------------------------NOTIFY CONTROLLER---------------------------------
            this.controller.insertUserInputIntoModel(choice);
            this.controller.changeTurn();
        }
        showPersonalRecap();
        System.out.println("---GAME ENDED---");
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
        String activePlayerNickname = this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getNickname();
        System.out.println("Tocca a te player: " + activePlayerNickname + "!");
        System.out.println("Stato della board attuale:");
        System.out.println(this.getModel().getBoard());
    }

    /**
     * Identifies the choices of the player during the turn, the ones related to tiles selection.
     *
     * @param iterationCount used to iterate on the player's choice (the calls for input insertion in a turn are limited).
     * @param isRowBeingChosen {@code true} if and only if the selected bookshelf's row is the one passed as parameter, {@code false} otherwise.
     * @return the index of the chosen rowColumnTile.
     *
     * @see it.polimi.ingsw.model.Player
     * @see Choice
     * @see it.polimi.ingsw.model.Bookshelf
     * @see it.polimi.ingsw.model.tile.Tile
     */
    private int rowColumnTileChoiceFromBoard(int iterationCount, boolean isRowBeingChosen) {
        boolean isInsertCorrect = false;
        int choice = 0;
        while (!isInsertCorrect) {
            System.out.println("Inserisci la " + (isRowBeingChosen ? "riga" : "colonna") + " della " + (iterationCount + 1) + "° tessera che vuoi prendere:");
            try {
                choice = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
            } catch (InputMismatchException e) {
                System.err.println("Hai inserito un valore non valido, riprova!");
                CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            }

            if (choice > 0 && choice <= (isRowBeingChosen ? this.getModel().getBoard().getNumberOfRows() : this.getModel().getBoard().getNumberOfColumns())) {
                isInsertCorrect = true;
            } else {
                System.err.println("Inserisci una " + (isRowBeingChosen ? "riga" : "colonna") + " valida (Un numero compreso tra 1 e " + (isRowBeingChosen ? this.getModel().getBoard().getNumberOfRows() : this.getModel().getBoard().getNumberOfColumns()) + "!)");
            }
        }
        return choice;
    }

    /**
     * Identifies the player's chosen column in the bookshelf.
     *
     * @param iterationCount used to iterate on the player's choice (the calls for input insertion in a turn are limited).
     * @return the index of the selected column.
     *
     * @see it.polimi.ingsw.model.Bookshelf
     * @see it.polimi.ingsw.model.Player
     * @see Choice
     */
    private int bookshelfColumnChoice(int iterationCount) {
        boolean isInsertCorrect;
        int chosenColumn = 0;
        do {
            isInsertCorrect = true;
            System.out.println("Scegli la colonna in cui vuoi inserire le tue tessere:");
            try {
                chosenColumn = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
                if (chosenColumn <= 0 || chosenColumn > this.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns()) {
                    isInsertCorrect = false;
                    System.err.println("Hai scelto una colonna al di fuori dei limiti della bookshelf, inserisci un valore compreso tra" +
                            " 1 e " + this.getModel().getPlayers().get(0).getBookshelf().getNumberOfColumns() + "!");
                } else {
                    if (this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf().getNumberOfEmptyCellsInColumn(chosenColumn - 1) < iterationCount) {
                        isInsertCorrect = false;
                        System.err.println("Hai scelto una colonna con un numero di spazi liberi non sufficiente per inserire le tessere scelte, riprova!");
                    }
                }
            } catch (InputMismatchException ignored) {
                isInsertCorrect = false;
                System.err.println("Non hai inserito un valore valido, riprova!");
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
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.network.Client
     * @see it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer.DisconnectPlayerCommand
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.utils.CommandQueue
     * @see it.polimi.ingsw.utils.CommandReader
     */
    @Override
    public Choice askPlayer() {
        while (true) {
            System.out.println("Seleziona l'azione(Digita il numero associato all'azione):");
            System.out.println("1)Recap situazione personale");
            System.out.println("2)Scegli tessere");
            System.out.println("3)Invia messaggio tramite chat");
            System.out.println("4)Disconnettiti");
            String input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
            switch (input) {
                case "1" -> {
                    showPersonalRecap();
                }
                case "2" -> {
                    System.out.println("La situazione della board attuale:");
                    System.out.println(this.getModel().getBoard());

                    int counter = 0, firstRow = 0, firstColumn = 0;

                    Choice playerChoice = new Choice();
                    Direction directionToCheck = null;
                    int maxNumberOfCellsFreeInBookshelf;
                    //---------------------------------SCELTA COORDINATE TESSERE---------------------------------
                    maxNumberOfCellsFreeInBookshelf = this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf().getMaxNumberOfCellsFreeInBookshelf();
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
                                    playerChoice.addTile(this.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                    playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                }
                                case 1 -> {
                                    Direction res = checkIfInLine(row, column, firstRow, firstColumn);
                                    if (res != null) {
                                        directionToCheck = res;
                                        counter++;
                                        playerChoice.addTile(this.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                    }
                                }
                                case 2 -> {
                                    if (checkIfInLine(row - 1, column - 1, playerChoice.getTileCoordinates(), directionToCheck)) {
                                        counter++;
                                        playerChoice.addTile(this.getModel().getBoard().getTiles()[row - 1][column - 1]);
                                        playerChoice.addCoordinates(new Coordinates(row - 1, column - 1));
                                    }
                                }
                                default -> {
                                    System.err.println("[INPUT:ERROR]: Unexpected number of chosen tiles, found: " + counter + ", expected value < 3");
                                }
                            }
                        }
                        if (counter < maxNumberOfCellsFreeInBookshelf) {
                            if (counter > 0 && counter != 3) {
                                do {
                                    System.out.println("Vuoi continuare? (Digita \"SI\" per continuare, \"NO\" per fermarti)");
                                    input = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
                                } while (!input.equalsIgnoreCase("SI") && !input.equalsIgnoreCase("NO"));
                            }
                        } else {
                            input = "NO";
                        }
                    } while (!input.equalsIgnoreCase("NO") && counter < 3);

                    //---------------------------------SCELTA COLONNA---------------------------------
                    System.out.println(this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex()).getBookshelf());

                    int chosenColumn = bookshelfColumnChoice(counter);

                    playerChoice.setChosenColumn(chosenColumn - 1);
                    //---------------------------------SCELTA ORDINE---------------------------------

                    if (counter == 1) {
                        playerChoice.setTileOrder(new int[]{0});
                    } else {
                        System.out.println("Digita l'ordine con cui vuoi inserire le tessere (1 indica la prima tessera scelta, 2 la seconda e 3 la terza, ES: 1,3,2)");
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
                                    System.err.println("Hai inserito delle cifre non coerenti con il numero di tessere scelte");
                                }
                            } else {
                                System.err.println("Hai inserito un numero di cifre diverso dal numero di tessere scelte. Oppure hai effettuato un inserimento che non rispetta" +
                                        " la formattazione richiesta, riprova!");
                            }
                        } while (!isInsertCorrect);
                    }

                    return playerChoice;
                }
                case "3" -> {
                    String receiver = null;

                    System.out.println("Che tipo di messaggio vuoi inviare? Pubblico (B)/ Privato (P)");
                    String messageType = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();

                    if (messageType.equals("P")) {
                        System.out.println("A chi vuoi inviare il messaggio?");
                        receiver = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();
                    }

                    System.out.println("Inserisci il tuo messaggio qui");
                    String content = CommandReader.standardCommandQueue.waitAndGetFirstCommandAvailable();

                    if (messageType.equals("P")) {
                        this.controller.sendPrivateMessage(this.getNickname(), receiver, content);
                    } else if (messageType.equals("B")) {
                        this.controller.sendBroadcastMessage(this.getNickname(), content);
                    } else {
                        System.err.println("La tipologia di messaggio specificata non è riconosciuta, utilizzarne una valida");
                    }
                    //   } while (!isInsertCorrect);

                }
                case "4" -> {
                    this.controller.disconnectPlayer(this.getNickname());
                    System.err.println("Ti sei disconnesso dalla partita");
                    System.exit(0);
                }
                default -> {
                    System.err.println("Non hai inserito un valore valido, riprova! (Inserisci uno degli indici del menù)");
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
     * @param row the row in selection.
     * @param column the column in selection.
     * @param firstRow the inspection starting row.
     * @param firstColumn the inspection starting column.
     * @return the direction of the two current tiles chosen.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.tile.Tile
     */
    private Direction checkIfInLine(int row, int column, int firstRow, int firstColumn) {
        if (row == firstRow && column == firstColumn) {
            System.err.println("Non puoi scegliere di nuovo una tessera già scelta, riprova!");
            return null;
        }
        if ((row == firstRow) && (column - 1 == firstColumn || column + 1 == firstColumn)) {
            return Direction.HORIZONTAL;
        }
        if ((column == firstColumn) && (row - 1 == firstRow || row + 1 == firstRow)) {
            return Direction.VERTICAL;
        }
        System.err.println("Le tessere selezionate devono formare una linea retta ed essere adiacenti, riprova!");
        return null;
    }

    /**
     * Check if the {@code Tile} chosen by the {@code Player} is already
     * present in the current selection at the given coordinates and
     * if the {@code Player} has properly inserted all the tiles in the {@code Board}.
     *
     * @param row the row in selection.
     * @param column the column in selection.
     * @param prevTilesCoordinates the coordinates of the Tiles which have already been placed on the {@code Board}.
     * @param directionToCheck the direction (on row/column) which is selected for inspection.
     * @return {@code true} if and only if the {@code Tile}
     *          has already been placed in a previous turn.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     */
    private boolean checkIfInLine(int row, int column, List<Coordinates> prevTilesCoordinates, Direction directionToCheck) {
        if (prevTilesCoordinates.contains(new Coordinates(row, column))) {
            System.err.println("Non puoi scegliere di nuovo una tessera già scelta, riprova!");
            return false;
        }
        switch (directionToCheck) {
            case HORIZONTAL -> {
                if (row != prevTilesCoordinates.get(0).getX()) {
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getY() == column + 1 || coordinates.getY() == column - 1) {
                            return true;
                        }
                    }
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                }
                return false;
            }
            case VERTICAL -> {
                if (column != prevTilesCoordinates.get(0).getY()) {
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
                    return false;
                } else {
                    for (Coordinates coordinates : prevTilesCoordinates) {
                        if (coordinates.getX() == row + 1 || coordinates.getX() == row - 1) {
                            return true;
                        }
                    }
                    System.err.println("Le tessere selezionate devono formare una linea retta e devono essere adiacenti l'una all'altra, riprova!");
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
     * @param row is the index of the selected row.
     * @param column is the index of the selected column.
     * @return {@code true} if and only if the {@code boardMatrix}
     *                presents a collectable tile in the position
     *                identified though the given coordinates;
     *                {@code false} otherwise
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.tile.Tile
     */
    private boolean checkIfPickable(int row, int column) {
        BoardView board = this.getModel().getBoard();
        TileView[][] boardMatrix = board.getTiles();

        if (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) {
            if ((row != 0 && (boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                    (row != board.getNumberOfRows() && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                    (column != board.getNumberOfColumns() && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
                    (column != 0 && (boardMatrix[row][column - 1] == null || boardMatrix[row][column - 1].getColor() == null))) {
                return true;
            } else {
                System.err.println("Impossibile prendere la tessera (Ha tutti i lati occupati), riprova!");
            }
        } else {
            System.err.println("Non è presente nessuna tessera nella cella selezionata, riprova!");
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
    //TODO: remove from UI
    public void showPersonalRecap() {
        PlayerView activePlayer = this.getModel().getPlayers().stream().filter(player -> player.getNickname().equals(this.getNickname())).toList().get(0);
        //PlayerView activePlayer = this.getModel().getPlayers().get(this.getModel().getActivePlayerIndex());
        BookshelfView playerBookshelf = activePlayer.getBookshelf();
        PersonalGoalView playerPersonalGoal = activePlayer.getPersonalGoal();
        List<ScoreTileView> playerGoalTiles = activePlayer.getGoalTiles();

        int playerScore = activePlayer.score();

        List<CommonGoalView> commonGoals = this.getModel().getCommonGoals();

        System.out.println("Ecco il tuo recap:");
        System.out.println("Stato della tua bookshelf:\n" + playerBookshelf + "\n" +
                "Il tuo obiettivo personale:\n" + playerPersonalGoal + "\n" +
                "Gli obiettivi comuni sono:\n" + commonGoals.get(0) + "\n" + commonGoals.get(1) + "\n" +
                "Obiettivi comuni completati: Obiettivo1:" + (playerGoalTiles.size() > 0 && playerGoalTiles.get(0) != null ? playerGoalTiles.get(0).getValue() : "/") +
                ", Obiettivo2:" + (playerGoalTiles.size() > 1 && playerGoalTiles.get(1) != null ? playerGoalTiles.get(1).getValue() : "/") + ", Vittoria:" +
                (playerGoalTiles.size() > 2 && playerGoalTiles.get(2) != null ? playerGoalTiles.get(2).getValue() : "/") + " (Valore delle goalTile)" + "\n" +
                "Il tuo punteggio attuale " + playerScore);
    }

    /**
     * Prints the Game Title Screen.
     *
     * @see it.polimi.ingsw.model.Game
     */
    public void printTitleScreen(){

        System.out.println ("███╗░░░███╗██╗░░░██╗░░░░░░░░░██████╗██╗░░██╗███████╗██╗░░░░░███████╗██╗███████╗");
        System.out.println ("████╗░████║╚██╗░██╔╝░░░░░░░░██╔════╝██║░░██║██╔════╝██║░░░░░██╔════╝██║██╔════╝");
        System.out.println ("██╔████╔██║░╚████╔╝░░░░░░░░░╚█████╗░███████║█████╗░░██║░░░░░█████╗░░██║█████╗░░");
        System.out.println ("██║╚██╔╝██║░░╚██╔╝░░░░░░░░░░░╚═══██╗██╔══██║██╔══╝░░██║░░░░░██╔══╝░░██║██╔══╝░░");
        System.out.println ("██║░╚═╝░██║░░░██║░░░░░░░░░░░██████╔╝██║░░██║███████╗███████╗██║░░░░░██║███████╗");
        System.out.println ("╚═╝░░░░░╚═╝░░░╚═╝░░░░░░░░░░░╚═════╝░╚═╝░░╚═╝╚══════╝╚══════╝╚═╝░░░░░╚═╝╚══════╝");
        System.out.println ("                           GAME OF THE YEAR EDITION");
    }

/*




███╗░░░███╗██╗░░░██╗░░░░░░░░░██████╗██╗░░██╗███████╗██╗░░░░░███████╗██╗███████╗
████╗░████║╚██╗░██╔╝░░░░░░░░██╔════╝██║░░██║██╔════╝██║░░░░░██╔════╝██║██╔════╝
██╔████╔██║░╚████╔╝░░░░░░░░░╚█████╗░███████║█████╗░░██║░░░░░█████╗░░██║█████╗░░
██║╚██╔╝██║░░╚██╔╝░░░░░░░░░░░╚═══██╗██╔══██║██╔══╝░░██║░░░░░██╔══╝░░██║██╔══╝░░
██║░╚═╝░██║░░░██║░░░░░░░░░░░██████╔╝██║░░██║███████╗███████╗██║░░░░░██║███████╗
╚═╝░░░░░╚═╝░░░╚═╝░░░░░░░░░░░╚═════╝░╚═╝░░╚═╝╚══════╝╚══════╝╚═╝░░░░░╚═╝╚══════╝
                           GAME OF THE YEAR EDITION




░██████╗░░█████╗░████████╗██╗░░░██╗░░░░░░░░███████╗██████╗░██╗████████╗██╗░█████╗░███╗░░██╗
██╔════╝░██╔══██╗╚══██╔══╝╚██╗░██╔╝░░░░░░░░██╔════╝██╔══██╗██║╚══██╔══╝██║██╔══██╗████╗░██║
██║░░██╗░██║░░██║░░░██║░░░░╚████╔╝░░░░░░░░░█████╗░░██║░░██║██║░░░██║░░░██║██║░░██║██╔██╗██║
██║░░╚██╗██║░░██║░░░██║░░░░░╚██╔╝░░░░░░░░░░██╔══╝░░██║░░██║██║░░░██║░░░██║██║░░██║██║╚████║
╚██████╔╝╚█████╔╝░░░██║░░░░░░██║░░░░░░░░░░░███████╗██████╔╝██║░░░██║░░░██║╚█████╔╝██║░╚███║
░╚═════╝░░╚════╝░░░░╚═╝░░░░░░╚═╝░░░░░░░░░░░╚══════╝╚═════╝░╚═╝░░░╚═╝░░░╚═╝░╚════╝░╚═╝░░╚══╝
*/

}
