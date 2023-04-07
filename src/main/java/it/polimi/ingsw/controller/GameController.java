package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.TileView;
import it.polimi.ingsw.utils.ObservableType;
import it.polimi.ingsw.utils.Observer;

import java.util.Collections;
import java.util.List;


public class GameController implements Observer<UI, ObservableType> {
    private final Game model;
    private final UI view;

    public GameController(Game model, UI view) {
        this.model = model;
        this.view = view;
    }

    //Soluzione 1, utilizzare una classe che rappresenti la scelta del giocatore delle tessere e della colonna:
    //public class Choice {
    //      private List<Tile> choosedTiles;        OPPURE          private Tile[] choosedTiles; --> Dichiarandolo di 3 elementi;
    //      private int[] positions;
    //      private int choosedColumn;
    //
    //      ......
    //}
    private void inizializeGame() {

    }


    //Trasposizione del metodo changeTurn già presente nella classe game
    public void changeTurn() {
        if (model.getBoard().numberOfTilesToRefill() != 0) {
            this.refillBoard();
        }

        updatePlayerScore(model.getPlayers().get(model.getActivePlayerIndex()));

        if (model.getActivePlayerIndex() == model.getPlayers().size() - 1) {
            model.setActivePlayerIndex(0);
        } else {
            model.setActivePlayerIndex(model.getActivePlayerIndex() + 1);
        }

    }

    //Trasposizione del metodo refillBoard già presente nella classe game
    private void refillBoard() {
        Collections.shuffle(model.getBag());

        List<Tile> drawedTiles = model.getBag().subList(0, model.getBoard().numberOfTilesToRefill());
        model.getBoard().addTiles(drawedTiles);
        drawedTiles.clear();
    }

    //Trasposizione del metodo updatePlayerScore già presente nella classe game
    private void updatePlayerScore(Player player) {
        player.score();
    }

    private void computeRecapPlayer() {
        //Logica che restituisce alla view i dati del player nel turno di partita in cui li ha richiesti:
        //Es: il suo punteggio; lo stato della sua bookshelf; recap common goal da completare/completati con relative goal tile; avanzamento del suo personal goal...
    }

    @Override
    public void update(UI o, ObservableType arg) {
        if (o != view) {
            System.err.println("Discarding notification from " + o);
            return;
        } else {
            switch (arg.getEvent()) {
                case USER_INPUT -> {
                    Choice choice = (Choice) arg;
                    if (checkIfUserInputIsCorrect(choice)) {
                        insertUserInputIntoModel(choice);
                    }

                }
                case CHANGE_TURN -> {
                    this.changeTurn();
                }
            }
        }
    }

    private boolean checkIfUserInputIsCorrect(Choice choice) {
        List<TileView> choiceChoosenTiles = choice.getChosenTiles();
        int[] choiceTileOrder = choice.getTileOrder();
        int choiceColumn = choice.getChosenColumn();
        List<Coordinates> choiceTileCoordinates = choice.getTileCoordinates();

        if (choiceChoosenTiles.size() == choiceTileOrder.length && choiceTileOrder.length == choiceTileCoordinates.size()) {
            if (choiceColumn >= 0 && choiceColumn < model.getPlayers().get(0).getBookshelf().getNumberOfColumns()) {
                if (checkIfCoordinatesArePlausible(choiceTileCoordinates)) {
                    return true;
                }
            }
        }
        System.err.println("User input data are incorrect");
        return false;
    }

    private boolean checkIfPickable(int x, int y) {
        Board board = model.getBoard();
        Tile[][] boardMatrix = board.getTiles();

        return (boardMatrix[x][y] != null || boardMatrix[x][y].getColor() != null) && (
                (x != 0 && (boardMatrix[x - 1][y] == null || boardMatrix[x - 1][y].getColor() == null)) ||
                        (x != board.getNumberOfRows() && (boardMatrix[x + 1][y] == null || boardMatrix[x + 1][y].getColor() == null)) ||
                        (y != board.getNumberOfColumns() && (boardMatrix[x][y + 1] == null || boardMatrix[x][y + 1].getColor() == null)) ||
                        (y != 0 && (boardMatrix[x][y - 1] == null || boardMatrix[x][y - 1].getColor() == null)));
    }

    private boolean checkIfCoordinatesArePlausible(List<Coordinates> coordinates) {
        for (Coordinates coordinate : coordinates) {
            if (!checkIfPickable(coordinate.getX(), coordinate.getY())) {
                return false;
            }
        }
        return true;
    }

    private void insertUserInputIntoModel(Choice playerChoice) {
        removeTilesFromBoard(playerChoice.getChosenTiles(), playerChoice.getTileCoordinates());
        addTilesToPlayerBookshelf(playerChoice.getChosenTiles(), playerChoice.getTileOrder(), playerChoice.getChosenColumn());


    }

    private void removeTilesFromBoard(List<TileView> chosenTiles, List<Coordinates> tileCoordinates) {
        Board board = model.getBoard();
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
        model.boardModified();
    }

    private void addTilesToPlayerBookshelf(List<TileView> chosenTiles, int[] positions, int chosenColumn) {
        Bookshelf bookshelf = model.getPlayers().get(model.getActivePlayerIndex()).getBookshelf();
        for (int i = 0; i < chosenTiles.size(); i++) {
            bookshelf.addTile(new Tile(chosenTiles.get(positions[i]).getColor()), chosenColumn);
        }
        model.bookshelfModified();
    }
}
