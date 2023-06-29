package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.TileView;
import it.polimi.ingsw.utils.OptionsValues;

import java.util.Collections;
import java.util.List;

/**
 * This class is an extension of the {@code ControllerState}, built following the state pattern
 * in order to distinguish through the different possible states of the {@code Game}.
 * It depicts its ongoing state, overriding the same methods of the previous states, with a series
 * of changes to verify if the conditions for game's ongoing are matched or not (checks if
 * there are still active players, if their choices can still affect the ongoing state...).
 * In case all the conditions are matched the controller keeps the {@code Game} in the {@code ON_GOING} state.
 *
 * @see GameState#ON_GOING
 * @see ControllerState
 * @see Game
 */
public class OnGoingState extends ControllerState {

    public OnGoingState(GameController controller) {
        super(controller);
    }

    /**
     * Change the turn in the context of the present state.
     *
     * @see ControllerState#changeTurn(String gamesStoragePath, String gamesStoragePathBackup)
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
        Game model = this.controller.getModel();
        if (model.getPlayers().stream().map(Player::isConnected).filter(connected -> connected).count() == OptionsValues.MIN_PLAYERS_TO_GO_ON_PAUSE) {
            this.controller.changeState(new InPauseState(this.controller));
            this.controller.getModel().setGameState(InPauseState.toEnum());
        } else {
            if (model.getActivePlayerIndex() == model.getPlayers().size() - 1) {
                model.setActivePlayerIndex(0);
            } else {
                model.setActivePlayerIndex(model.getActivePlayerIndex() + 1);
            }

            if (!model.getPlayers().get(model.getActivePlayerIndex()).isConnected()) {
                this.changeActivePlayer();
            }
        }
    }

    /**
     * Allows the active {@code Player} to make his {@code Choice}
     * in the context of input selection ({@code Tile}s, their position on the {@code Board},... ), through
     * the controller linked to the actual state.
     *
     * @param playerChoice the {@code Choice} made by the {@code Player} in the current selection.
     * @see Player
     * @see Choice
     * @see ControllerState#insertUserInputIntoModel(Choice)
     */
    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws WrongInputDataException {
        Game model = this.controller.getModel();
        Player currentPlayer = model.getPlayers().get(model.getActivePlayerIndex());
        if (checkIfUserInputIsCorrect(playerChoice)) {
            removeTilesFromBoard(playerChoice.getTileCoordinates());
            addTilesToPlayerBookshelf(playerChoice.getChosenTiles(), playerChoice.getTileOrder(), playerChoice.getChosenColumn());
        } else {
            throw new WrongInputDataException("[INPUT:ERROR]: User data not correct");
        }

        for (int i = 0; i < model.getCommonGoals().size(); i++) {
            int finalI = i;
            if (model.getCommonGoals().get(i).numberOfPatternRepetitionInBookshelf(currentPlayer.getBookshelf()) >= model.getCommonGoals().get(i).getNumberOfPatternRepetitionsRequired()
                    && currentPlayer.getScoreTiles().stream().map(ScoreTile::getCommonGoalID).noneMatch(elem -> elem == model.getCommonGoals().get(finalI).getId()) && model.getCommonGoals().get(i).getScoreTiles().size() != 0) {
                currentPlayer.setSingleScoreTile(model.getCommonGoals().get(i).getScoreTiles().remove(0), i);
                currentPlayer.getScoreTiles().get(i).setPlayerID(model.getActivePlayerIndex());
            }
        }

        if (this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getBookshelf().isFull()) {
            currentPlayer.setSingleScoreTile(new ScoreTile(OptionsValues.WINNING_TILE_VALUE, model.getActivePlayerIndex(), model.getCommonGoals().size()), model.getCommonGoals().size());
            this.controller.changeState(new FinishingState(this.controller));
            this.controller.getModel().setGameState(FinishingState.toEnum());
        } else {
            //Necessary because we ALWAYS need a feedback to the client in order to wait client-side for the updated model.
            //Without this else we would receive only ONE time a notification that tell us that the state has changed, and we can't know when this will happen client-side
            this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        }
    }

    /**
     * This method checks if the active {@code Player} input has been entered correctly.
     * Whether if order or coordinates or type of {@code Tile}s chosen has been mistaken,
     * prints an error message. To effectively produce the check the method also needs to access
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
     */
    private boolean checkIfUserInputIsCorrect(Choice choice) {
        List<TileView> choiceChosenTiles = choice.getChosenTiles();
        int[] choiceTileOrder = choice.getTileOrder();
        int choiceColumn = choice.getChosenColumn();
        List<Coordinates> choiceTileCoordinates = choice.getTileCoordinates();

        Bookshelf currentPlayerBookshelf = this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getBookshelf();

        if (choiceChosenTiles.size() == choiceTileOrder.length && choiceTileOrder.length == choiceTileCoordinates.size()) {
            if (choiceColumn >= 0 && choiceColumn < currentPlayerBookshelf.getNumberOfColumns() && currentPlayerBookshelf.getNumberOfEmptyCellsInColumn(choiceColumn) >= choiceChosenTiles.size()) {
                if (checkIfCoordinatesArePlausible(choiceTileCoordinates)) {
                    return true;
                }
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
                row == board.getNumberOfRows() - 1 || column == board.getNumberOfColumns() - 1 ||
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
     * Only the specified receiver will be able to read the message
     * in any chat implementation. It builds a new object message at each call, setting
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
     * All the players will be able to read the message
     * in any chat implementation. It builds a new object message at each call, setting
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
     * This method is used to reconnect the {@code Player} in case of temporary disconnection.
     *
     * @param nickname is the nickname of the previously disconnected {@code Player}.
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
     * in on_going state.
     *
     * @see Game
     */
    @Override
    public void tryToResumeGame() {
        //Necessary in case I call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then I'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is going, so do nothing...
    }

    /**
     * Sets the number of player necessary to start the game
     * Does nothing in this implementation since game is
     * in on_going state.
     */
    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        //Necessary in case I call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then I'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is going, so do nothing...
    }

    /**
     * Checks if the number of players in the current lobby is exceeding the game's set number of players
     * Does nothing in this implementation since game is
     * in on_going state.
     *
     * @param chosenNumberOfPlayers number of players chosen by the first player.
     * @see Game#getNumberOfPlayersToStartGame()
     */
    @Override
    public void checkExceedingPlayer(int chosenNumberOfPlayers) {
        //Necessary in case I call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then I'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is going, so do nothing...
    }

    /**
     * Method that starts the {@code Game}
     * Does nothing in this implementation since game is
     * in on_going state.
     */
    @Override
    public void startGame(int numberOfCommonGoalCards) {
        //Necessary in case I call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then I'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is going, so do nothing...
    }

    /**
     * Disconnects the selected {@code Player} from the {@code Game}
     * by changing his connectivity state.
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

        if (model.getPlayers().size() == model.getPlayers().stream().filter(player -> !player.isConnected()).count()) {
            this.controller.getModel().setGameState(GameState.RESET_NEEDED);
        } else {
            if (model.getPlayers().get(model.getActivePlayerIndex()).getNickname().equals(nickname)) {
                this.changeActivePlayer();
            } else if (model.getPlayers().stream().map(Player::isConnected).filter(connected -> connected).count() == OptionsValues.MIN_PLAYERS_TO_GO_ON_PAUSE) {
                this.controller.changeState(new InPauseState(this.controller));
                this.controller.getModel().setGameState(InPauseState.toEnum());
            }
        }
    }

    /**
     * Restores the current game for the considered player.
     *
     * Does nothing in this implementation since game is
     * in finishing state.
     * @param server           the server to which the model notifies its changes.
     * @param nickname         player's nickname that requested the restore.
     * @param gamesStoragePath the path where are stored the games.
     * @see Player
     * @see Game
     */
    @Override
    public void restoreGameForPlayer(GameListener server, String nickname, String gamesStoragePath) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //Game is going, so do nothing...
    }

    /**
     * Returns the current {@code State} of the {@code Game}.
     *
     * @return the ONGOING STATE of the {@code Game}.
     * @see Game
     * @see GameState#ON_GOING
     */
    public static GameState toEnum() {
        return GameState.ON_GOING;
    }
}
