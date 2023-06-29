package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.TileView;

import java.util.Collections;
import java.util.List;

/**
 * This class is an extension of the {@code ControllerState}, built following the state pattern
 * in order to distinguish through the different possible states of the {@code Game}.
 * It depicts its final state. It takes care of the last round of the game
 * At the end of the round the controller force the entrance in the {@code RESET_NEEDED} state.
 *
 * @see GameState#RESET_NEEDED
 * @see ControllerState
 * @see Game
 */
public class FinishingState extends ControllerState {
    /**
     * Class constructor.
     * Instantiate a {@code FinishingState}
     *
     * @param controller controller that delegate its tasks to this state
     */
    public FinishingState(GameController controller) {
        super(controller);
    }

    /**
     * The method controls in first place if the {@code Game} has ended,
     * inspecting the presence of active players.
     * In case the inspection fails it is signaled a change in the {@code GameState}.
     * Otherwise, checks if there are tiles to be refilled and calls the {@code refillBoard} method.
     *
     * @see FinishingState#refillBoard()
     */
    @Override
    public void changeTurn(String gamesStoragePath, String gamesStoragePathBackup) {
        if (this.controller.getModel().getBoard().numberOfTilesToRefill() != 0) {
            this.refillBoard();
        }
        changeActivePlayer();

        this.controller.getModel().saveGame(gamesStoragePath, gamesStoragePathBackup);
    }

    /**
     * Shuffle the tiles contained in the object bag,
     * Refills the {@code Board} with a new set of tiles.
     *
     * @see Game#getBag()
     * @see Collections#shuffle(List)
     */
    private void refillBoard() {
        Collections.shuffle(this.controller.getModel().getBag());

        List<Tile> drawnTiles = this.controller.getModel().getBag().subList(0, this.controller.getModel().getBoard().numberOfTilesToRefill());
        this.controller.getModel().getBoard().addTiles(drawnTiles);
        drawnTiles.clear();
    }

    /**
     * Verifies if whether the current {@code Player}
     * is considered active or not, and change the current
     * active player to the first connected player in the
     * round order
     *
     * @see GameController#getModel()
     * @see Game#getActivePlayerIndex()
     */
    private void changeActivePlayer() {
        if (this.controller.getModel().getActivePlayerIndex() == this.controller.getModel().getPlayers().size() - 1) {
            this.controller.getModel().setGameState(GameState.RESET_NEEDED);
        } else {
            this.controller.getModel().setActivePlayerIndex(this.controller.getModel().getActivePlayerIndex() + 1);
        }
    }

    /**
     * Check if user input is correct.
     * then proceeds to deploy the chosen tiles in the proper order in the bookshelf and
     * remove them from the board.
     * If the initial check rejects the {@code Choice}, an {@code WrongInputDataException} is thrown.
     *
     * @param playerChoice is the player's choice
     * @throws WrongInputDataException occurs when data has an unexpected value.
     * @see Choice
     * @see #checkIfUserInputIsCorrect(Choice playerChoice)
     * @see #removeTilesFromBoard(List)
     * @see #addTilesToPlayerBookshelf(List, int[], int)
     */
    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws WrongInputDataException {
        if (checkIfUserInputIsCorrect(playerChoice)) {
            removeTilesFromBoard(playerChoice.getTileCoordinates());
            addTilesToPlayerBookshelf(playerChoice.getChosenTiles(), playerChoice.getTileOrder(), playerChoice.getChosenColumn());
        } else {
            throw new WrongInputDataException("[INPUT:ERROR]: User data not correct");
        }
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
    }

    /**
     * This method checks if the active {@code Player} input has been entered correctly.
     * Whether if order or coordinates or type of {@code Tile}s chosen has been mistaken,
     * throw a {@code WrongInputDataException}. To effectively produce the check the method also access
     * the {@code Bookshelf} of the player making the choice.
     *
     * @param choice is the player's {@code Choice}.
     * @return {@code true} if and only if the player has entered his choice in a correct format,
     * {@code false} otherwise.
     * @see Tile
     * @see Player
     * @see Choice
     * @see Bookshelf
     * @see GameController#getModel()
     * @see Game#getActivePlayerIndex()
     * @see Player#getNickname()
     * @see Player#getBookshelf()
     * @see #checkIfCoordinatesArePlausible(List)
     */
    private boolean checkIfUserInputIsCorrect(Choice choice) {
        List<TileView> choiceChosenTiles = choice.getChosenTiles();
        int[] choiceTileOrder = choice.getTileOrder();
        int choiceColumn = choice.getChosenColumn();
        List<Coordinates> choiceTileCoordinates = choice.getTileCoordinates();

        Bookshelf currentPlayerBookshelf = this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getBookshelf();

        if (choiceChosenTiles.size() == choiceTileOrder.length && choiceTileOrder.length == choiceTileCoordinates.size()) {
            if (choiceColumn >= 0 && choiceColumn < currentPlayerBookshelf.getNumberOfColumns() && currentPlayerBookshelf.getNumberOfEmptyCellsInColumn(choiceColumn) >= choiceChosenTiles.size()) {
                return checkIfCoordinatesArePlausible(choiceTileCoordinates);
            }
        }
        return false;
    }

    /**
     * Used to check if the coordinates entered by the player correspond to a plausible positioning
     * on the {@code Board} for his list of tiles.
     *
     * @param coordinates represents the list of the coordinates being checked.
     * @return {@code true} if and only if the player has entered all the {@code Tile}'s coordinates in a correct format,
     * {@code false} otherwise.
     * @see Tile
     * @see Board
     * @see #checkIfPickable(int, int)
     */
    private boolean checkIfCoordinatesArePlausible(List<Coordinates> coordinates) {
        for (Coordinates coordinate : coordinates) {
            if (!checkIfPickable(coordinate.getX(), coordinate.getY())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Used to check if at the given coordinates it is possible to pick up a {@code Tile}.
     *
     * @param row    is the row of the checked {@code Tile}.
     * @param column is the column of the checked {@code Tile}.
     * @return {@code true} if and only if the {@code Tile}'s can be picked,
     * {@code false} otherwise.
     * @see Tile
     */
    private boolean checkIfPickable(int row, int column) {
        Board board = this.controller.getModel().getBoard();
        Tile[][] boardMatrix = board.getTiles();

        return (boardMatrix[row][column] != null && boardMatrix[row][column].getColor() != null) && (
                (row != 0 && (boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                        (row != board.getNumberOfRows() - 1 && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                        (column != board.getNumberOfColumns() - 1 && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
                        (column != 0 && (boardMatrix[row][column - 1] == null || boardMatrix[row][column - 1].getColor() == null)));
    }

    /**
     * This method implements the tiles removal from the {@code Board}.
     * The {@code Player} select a list of {@code Tile}s whose coordinates are passed
     * in order to remove them.
     *
     * @param tileCoordinates is the list of the coordinates associated
     *                        to the respective tiles in the {@code chosenTiles} list.
     * @see Board
     * @see Board#removeTiles(List)
     * @see Player
     */
    private void removeTilesFromBoard(List<Coordinates> tileCoordinates) {
        Board board = this.controller.getModel().getBoard();
        board.removeTiles(tileCoordinates);
    }

    /**
     * Used to deploy the tiles chosen from the {@code Board} by {@code Player}
     * in the correspondent {@code Bookshelf}
     *
     * @param chosenTiles  is the list of the selected Tiles.
     * @param positions    are the positions selected for each tile during its placing in the {@code Bookshelf}.
     * @param chosenColumn the column of the bookshelf selected for placing the tiles.
     * @see Bookshelf
     * @see Player
     * @see Board
     */
    private void addTilesToPlayerBookshelf(List<TileView> chosenTiles, int[] positions, int chosenColumn) {
        Bookshelf bookshelf = this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getBookshelf();
        for (int i = 0; i < chosenTiles.size(); i++) {
            bookshelf.addTile(new Tile(chosenTiles.get(positions[i]).getColor()), chosenColumn);
        }
    }

    /**
     * This method is used to stream a message privately.
     * Only the specified receiver will be able to read the message.
     * It builds a new object message at each call, setting
     * the {@code nickname}s of the receiving {@code Player}s and its message type to {@code PRIVATE}.
     *
     * @param receiver the {@code Player} receiving the message.
     * @param sender   the {@code Player} sending the message.
     * @param content  the text of the message being sent.
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        Message message = new Message(MessageType.PRIVATE, receiver, sender, content);
        for (Player player : this.controller.getModel().getPlayers()) {
            //sender and receiver will see the message, in order to keep the full history
            if (player.getNickname().equals(receiver) || player.getNickname().equals(sender)) {
                player.addMessage(message);
            }
        }

    }

    /**
     * This method is used to stream a message in broadcast mode.
     * All the players will be able to read the message.
     * It builds a new object message at each call, setting
     * the {@code nickname} of the sending {@code Player} and its message type to {@code BROADCAST}.
     *
     * @param sender  the {@code Player} sending the message.
     * @param content the text of the message being sent.
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    @Override
    public void sendBroadcastMessage(String sender, String content) {
        for (Player player : this.controller.getModel().getPlayers()) {
            Message message = new Message(MessageType.BROADCAST, player.getNickname(), sender, content);
            player.addMessage(message);
        }

    }

    /**
     * This method is used to reconnect the {@code Player} in case it was disconnected.
     *
     * @param nickname is the nickname of the previously disconnected {@code Player}.
     * @throws LobbyIsFullException thrown if the lobby is already full
     */
    @Override
    public void addPlayer(String nickname) throws LobbyIsFullException {
        //Reconnecting player
        if (this.controller.getModel().getPlayerFromNickname(nickname) == null) {
            throw new LobbyIsFullException("Cannot access a game: Lobby is full or you were not part of it at the start of the game");
        } else {
            this.controller.getModel().getPlayerFromNickname(nickname).setConnected(true);
        }
    }

    /**
     * Used to try to resume the game if it is in pause.
     * Does nothing in this implementation since game is
     * in finishing state.
     *
     * @see Game
     */
    @Override
    public void tryToResumeGame() {
        //Necessary in case I call this method while I'm in Finishing state (SHOULDN'T BE HAPPENING but if happen then I'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is finishing, so do nothing...
    }

    /**
     * Sets the number of player necessary to start the game
     * Does nothing in this implementation since game is
     * in finishing state.
     */
    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        //Necessary in case I call this method while I'm in Finishing state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is finishing, so do nothing...
    }

    /**
     * Checks if the number of players in the current lobby is exceeding the game's set number of players
     * Does nothing in this implementation since game is
     * in finishing state.
     *
     * @param chosenNumberOfPlayers number of players chosen by the first player.
     * @see Game#getNumberOfPlayersToStartGame()
     */
    @Override
    public void checkExceedingPlayer(int chosenNumberOfPlayers) {
        //Necessary in case I call this method while I'm in Finishing state (SHOULDN'T BE HAPPENING but if happen then I'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is finishing, so do nothing...
    }

    /**
     * Method that starts the {@code Game}
     * Does nothing in this implementation since game is
     * in finishing state.
     */
    @Override
    public void startGame(int numberOfCommonGoalCards) {
        //Necessary in case I call this method while I'm in Finishing state (SHOULDN'T BE HAPPENING but if happen then I'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is finishing, so do nothing...
    }

    /**
     * Disconnects the selected {@code Player} from the {@code Game}
     * by changing his connectivity state.
     * (only possible because the {@code Game} has already started).
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     * @see Player
     * @see Game
     * @see Player#setConnected(boolean)
     */
    @Override
    public void disconnectPlayer(String nickname) {
        Game model = this.controller.getModel();
        model.getPlayerFromNickname(nickname).setConnected(false);
        if (model.getPlayers().get(model.getActivePlayerIndex()).getNickname().equals(nickname)) {
            this.changeActivePlayer();
        }
    }

    /**
     * Restores the current game for the considered player.
     * <p>
     * Does nothing in this implementation since game is
     * in finishing state.
     *
     * @param server           the server to which the model notifies its changes.
     * @param nickname         player's nickname that requested the restore.
     * @param gamesStoragePath the path where are stored the games.
     * @see Player
     * @see Game
     */
    @Override
    public void restoreGameForPlayer(GameListener server, String nickname, String gamesStoragePath) {
        //Necessary in case I call this method while I'm in Finishing state (SHOULDN'T BE HAPPENING but if happen then I'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is finishing, so do nothing...
    }


    /**
     * Returns the current {@code State} of the {@code Game}.
     *
     * @return the FINISHING STATE of the {@code Game}.
     * @see Game
     * @see GameState#FINISHING
     */
    public static GameState toEnum() {
        return GameState.FINISHING;
    }
}
