package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.TileView;
import it.polimi.ingsw.network.exceptions.WrongInputDataException;

import java.util.Collections;
import java.util.List;

public class OnGoingState extends ControllerState {

    public OnGoingState(GameController controller) {
        super(controller);
    }

    @Override
    public void changeTurn() {
        if (this.controller.getModel().getBoard().numberOfTilesToRefill() != 0) {
            this.refillBoard();
        }
        changeActivePlayer();
    }

    private void refillBoard() {
        Collections.shuffle(this.controller.getModel().getBag());

        List<Tile> drawnTiles = this.controller.getModel().getBag().subList(0, this.controller.getModel().getBoard().numberOfTilesToRefill());
        this.controller.getModel().getBoard().addTiles(drawnTiles);
        drawnTiles.clear();
    }


    private void changeActivePlayer() {
        Game model = this.controller.getModel();
        if (model.getPlayers().stream().map(Player::isConnected).filter(connected -> connected).count() == 1) {
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

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws WrongInputDataException {
        Game model = this.controller.getModel();
        Player currentPlayer = model.getPlayers().get(model.getActivePlayerIndex());
        if (checkIfUserInputIsCorrect(playerChoice)) {
            removeTilesFromBoard(playerChoice.getChosenTiles(), playerChoice.getTileCoordinates());
            addTilesToPlayerBookshelf(playerChoice.getChosenTiles(), playerChoice.getTileOrder(), playerChoice.getChosenColumn());
        } else {
            throw new WrongInputDataException("[INPUT:ERROR]: User data not correct");
        }

        for (int i = 0; i < model.getCommonGoals().size(); i++) {
            int finalI = i;
            if (model.getCommonGoals().get(i).numberOfPatternRepetitionInBookshelf(currentPlayer.getBookshelf()) >= model.getCommonGoals().get(i).getNumberOfPatternRepetitionsRequired()
                    && currentPlayer.getGoalTiles().stream().map(ScoreTile::getCommonGoalID).noneMatch(elem -> elem == finalI)) {
                currentPlayer.setSingleScoreTile(model.getCommonGoals().get(i).getScoreTiles().remove(0), i);
                currentPlayer.getGoalTiles().get(i).setPlayerID(model.getActivePlayerIndex());
            }
        }

        if (this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getBookshelf().isFull()) {
            currentPlayer.setSingleScoreTile(new ScoreTile(1, model.getActivePlayerIndex(), model.getCommonGoals().size()), model.getCommonGoals().size());
            this.controller.changeState(new FinishingState(this.controller));
            this.controller.getModel().setGameState(FinishingState.toEnum());
        } else {
            //Necessary because we ALWAYS need a feedback to the client in order to wait client-side for the updated model.
            //Without this else we would receive only ONE time a notification that tell us that the state has changed, and we can't know when this will happen client-side
            this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        }
    }

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
        System.err.println("[INPUT:ERROR] User input data are incorrect");
        return false;
    }

    private boolean checkIfCoordinatesArePlausible(List<Coordinates> coordinates) {
        for (Coordinates coordinate : coordinates) {
            if (!checkIfPickable(coordinate.getX(), coordinate.getY())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfPickable(int row, int column) {
        Board board = this.controller.getModel().getBoard();
        Tile[][] boardMatrix = board.getTiles();

        return (boardMatrix[row][column] != null || boardMatrix[row][column].getColor() != null) && (
                row==board.getNumberOfRows()-1 || column== board.getNumberOfColumns()-1 ||
                (row != 0 && (boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                        (row != board.getNumberOfRows() - 1 && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                        (column != board.getNumberOfColumns() - 1 && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
                        (column != 0 && (boardMatrix[row][column - 1] == null || boardMatrix[row][column - 1].getColor() == null)));
    }

    private void removeTilesFromBoard(List<TileView> chosenTiles, List<Coordinates> tileCoordinates) {
        Board board = this.controller.getModel().getBoard();
        board.removeTiles(tileCoordinates);
    }

    private void addTilesToPlayerBookshelf(List<TileView> chosenTiles, int[] positions, int chosenColumn) {
        Bookshelf bookshelf = this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getBookshelf();
        for (int i = 0; i < chosenTiles.size(); i++) {
            bookshelf.addTile(new Tile(chosenTiles.get(positions[i]).getColor()), chosenColumn);
        }
    }

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

    @Override
    public void sendBroadcastMessage(String sender, String content) {
        for (Player player : this.controller.getModel().getPlayers()) {
            Message message = new Message(MessageType.BROADCAST, player.getNickname(), sender, content);
            player.addMessage(message);
        }

    }

    @Override
    public void addPlayer(String nickname) {
        //Reconnecting player
        this.controller.getModel().getPlayerFromNickname(nickname).setConnected(true);
    }

    @Override
    public void tryToResumeGame() {
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        //Game is going, so do nothing...
    }

    @Override
    public void startGame() {
        //Game is going, so do nothing...
    }

    @Override
    public void disconnectPlayer(String nickname) {
        Game model = this.controller.getModel();
        model.getPlayerFromNickname(nickname).setConnected(false);
        if (model.getPlayers().get(model.getActivePlayerIndex()).getNickname().equals(nickname)) {
            this.changeActivePlayer();
        }
        if (model.getPlayers().stream().map(Player::isConnected).filter(connected -> connected).count() == 1) {
            this.controller.changeState(new InPauseState(this.controller));
            this.controller.getModel().setGameState(InPauseState.toEnum());
        }
    }

    public static GameState toEnum() {
        return GameState.ON_GOING;
    }
}
