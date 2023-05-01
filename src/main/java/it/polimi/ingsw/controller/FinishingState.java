package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.TileView;

import java.util.Collections;
import java.util.List;

public class FinishingState extends ControllerState{
    public FinishingState(GameController controller) {super(controller);}
    @Override
    public void changeTurn() {
        Game model = this.controller.getModel();

        if(model.getActivePlayerIndex()==0) {
            //Game ended
            resetGame();
        } else {
            if (this.controller.getModel().getBoard().numberOfTilesToRefill() != 0) {
                this.refillBoard();
            }
            changeActivePlayer();
        }
    }

    private void refillBoard() {
        Collections.shuffle(this.controller.getModel().getBag());

        List<Tile> drawedTiles = this.controller.getModel().getBag().subList(0, this.controller.getModel().getBoard().numberOfTilesToRefill());
        this.controller.getModel().getBoard().addTiles(drawedTiles);
        drawedTiles.clear();
    }

    private void changeActivePlayer() {
        if (this.controller.getModel().getActivePlayerIndex() == this.controller.getModel().getPlayers().size() - 1) {
            this.controller.getModel().setActivePlayerIndex(0);
        } else {
            this.controller.getModel().setActivePlayerIndex(this.controller.getModel().getActivePlayerIndex() + 1);
        }
    }

    private void resetGame() {
        Game model = this.controller.getModel();
        model.setGameState(GameState.RESET_NEEDED);
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        if (checkIfUserInputIsCorrect(playerChoice)) {
            removeTilesFromBoard(playerChoice.getChosenTiles(), playerChoice.getTileCoordinates());
            addTilesToPlayerBookshelf(playerChoice.getChosenTiles(), playerChoice.getTileOrder(), playerChoice.getChosenColumn());
        } else {
            System.err.println("[INPUT:ERROR]: User data not correct");
        }
    }

    private boolean checkIfUserInputIsCorrect(Choice choice) {
        List<TileView> choiceChoosenTiles = choice.getChosenTiles();
        int[] choiceTileOrder = choice.getTileOrder();
        int choiceColumn = choice.getChosenColumn();
        List<Coordinates> choiceTileCoordinates = choice.getTileCoordinates();

        Bookshelf currentPlayerBookshelf = this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getBookshelf();

        if (choiceChoosenTiles.size() == choiceTileOrder.length && choiceTileOrder.length == choiceTileCoordinates.size()) {
            if (choiceColumn >= 0 && choiceColumn < currentPlayerBookshelf.getNumberOfColumns() && currentPlayerBookshelf.getNumberOfEmptyCellsInColumn(choiceColumn) >= choiceChoosenTiles.size()) {
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

    private boolean checkIfPickable(int x, int y) {
        Board board = this.controller.getModel().getBoard();
        Tile[][] boardMatrix = board.getTiles();

        return (boardMatrix[x][y] != null || boardMatrix[x][y].getColor() != null) && (
                (x != 0 && (boardMatrix[x - 1][y] == null || boardMatrix[x - 1][y].getColor() == null)) ||
                        (x != board.getNumberOfRows() && (boardMatrix[x + 1][y] == null || boardMatrix[x + 1][y].getColor() == null)) ||
                        (y != board.getNumberOfColumns() && (boardMatrix[x][y + 1] == null || boardMatrix[x][y + 1].getColor() == null)) ||
                        (y != 0 && (boardMatrix[x][y - 1] == null || boardMatrix[x][y - 1].getColor() == null)));
    }

    private void removeTilesFromBoard(List<TileView> chosenTiles, List<Coordinates> tileCoordinates) {
        Board board = this.controller.getModel().getBoard();
        int[] positions = new int[tileCoordinates.size() * 2];
        for (int i = 0; i < tileCoordinates.size() * 2; i++) {
            if (i % 2 == 0) {
                positions[i] = tileCoordinates.get(i / 2).getX();
            } else {
                positions[i] = tileCoordinates.get(i / 2).getY();
            }
        }
        Tile[] tilesToRemove = new Tile[chosenTiles.size()];
        for (int i = 0; i < chosenTiles.size(); i++) {
            tilesToRemove[0] = new Tile(chosenTiles.get(i).getColor());
        }
        board.removeTiles(tilesToRemove, positions);
    }

    private void addTilesToPlayerBookshelf(List<TileView> chosenTiles, int[] positions, int chosenColumn) {
        Bookshelf bookshelf = this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getBookshelf();
        for (int i = 0; i < chosenTiles.size(); i++) {
            bookshelf.addTile(new Tile(chosenTiles.get(positions[i]).getColor()), chosenColumn);
        }
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        //TODO: Implements message sending
    }

    @Override
    public void sendBroadcastMessage(String sender, String content) {
        //TODO: Implements message sending
    }

    @Override
    public void addPlayer(String nickname) {
        //Game is finishing, so do nothing...
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        //Game is finishing, so do nothing...
    }

    public static GameState toEnum() {
        return GameState.FINISHING;
    }
}
