package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.TileView;

import java.util.Collections;
import java.util.List;

public class FinishingState extends ControllerState {
    public FinishingState(GameController controller) {
        super(controller);
    }

    @Override
    public void changeTurn() {
        Game model = this.controller.getModel();

        if (model.getActivePlayerIndex() == model.getPlayers().size() - 1) {
            //Game ended
            model.setGameState(GameState.RESET_NEEDED);
        } else {
            if (this.controller.getModel().getBoard().numberOfTilesToRefill() != 0) {
                this.refillBoard();
            }
            changeActivePlayer();
        }
        this.controller.getModel().saveGame();
    }

    private void refillBoard() {
        Collections.shuffle(this.controller.getModel().getBag());

        List<Tile> drawnTiles = this.controller.getModel().getBag().subList(0, this.controller.getModel().getBoard().numberOfTilesToRefill());
        this.controller.getModel().getBoard().addTiles(drawnTiles);
        drawnTiles.clear();
    }

    private void changeActivePlayer() {
        if (this.controller.getModel().getActivePlayerIndex() == this.controller.getModel().getPlayers().size() - 1) {
            this.controller.getModel().setActivePlayerIndex(0);
        } else {
            this.controller.getModel().setActivePlayerIndex(this.controller.getModel().getActivePlayerIndex() + 1);
        }
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        if (checkIfUserInputIsCorrect(playerChoice)) {
            removeTilesFromBoard(playerChoice.getChosenTiles(), playerChoice.getTileCoordinates());
            addTilesToPlayerBookshelf(playerChoice.getChosenTiles(), playerChoice.getTileOrder(), playerChoice.getChosenColumn());
        } else {
            System.err.println("[INPUT:ERROR]: User data not correct");
        }
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
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
                (row != 0 && (boardMatrix[row - 1][column] == null || boardMatrix[row - 1][column].getColor() == null)) ||
                        (row != board.getNumberOfRows() -1 && (boardMatrix[row + 1][column] == null || boardMatrix[row + 1][column].getColor() == null)) ||
                        (column != board.getNumberOfColumns()-1 && (boardMatrix[row][column + 1] == null || boardMatrix[row][column + 1].getColor() == null)) ||
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
            if (player.getNickname().equals(receiver)) {
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
        //Game is finishing, so do nothing...
    }

    @Override
    public void startGame() {
        //Game is finishing, so do nothing...
    }

    @Override
    public void disconnectPlayer(String nickname) {
        Game model = this.controller.getModel();
        model.getPlayerFromNickname(nickname).setConnected(false);
        if (model.getPlayers().get(model.getActivePlayerIndex()).getNickname().equals(nickname)) {
            this.changeActivePlayer();
        }
    }


    @Override
    public void restoreGameForPlayer(String nickname) {
        //Game is finishing, so do nothing...
    }

    public static GameState toEnum() {
        return GameState.FINISHING;
    }
}
