package controller;

import model.Bookshelf;
import model.Game;
import model.Player;
import model.tile.Tile;
import view.UI;

import java.awt.print.Book;
import java.util.*;

public class GameController implements Observer {
    private final Game model;
    private final Bookshelf bookshelfController;
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
    private void checkIfPickable(int x, int y) {

    }
    private void checkIfInsertable(int column, int numTiles) {

    }
    private void addTilesToPlayerBookshelf(Choice pChoice) {
        //Togliere le tiles dalla board
        Bookshelf activePlayerBookshelf = model.getPlayers().get(model.getActivePlayerIndex()).getBookshelf();
        int column = pChoice.getChoosedColumn();
        List<Tile> pChoosedTiles = pChoice.getChoosedTiles();
        for (Tile pChoosedTile : pChoosedTiles) {
            activePlayerBookshelf.addTile(pChoosedTile, column);
            bookshelfController.addTile(pChoosedTile,column);
        }
    }
    //Trasposizione del metodo changeTurn già presente nella classe game
    private void changeTurn() {
        if(model.getBoard().needRefill() != 0) {
            this.refillBoard();
        }

        updatePlayerScore(model.getPlayers().get(model.getActivePlayerIndex()));

        if(model.getActivePlayerIndex() == model.getPlayers().size() - 1) {
            model.setActivePlayerIndex(0);
        } else {
            model.setActivePlayerIndex(model.getActivePlayerIndex()+1);
        }

    }
    //Trasposizione del metodo refillBoard già presente nella classe game
    private void refillBoard() {
        Collections.shuffle(model.getBag());

        List<Tile> drawedTiles = model.getBag().subList(0, model.getBoard().needRefill());
        model.getBoard().addTiles(drawedTiles);
        drawedTiles.clear();
    }
    //Trasposizione del metodo updatePlayerScore già presente nella classe game
    private void updatePlayerScore(Player player){
        player.score();
    }

    private void computeRecapPlayer() {
        //Logica che restituisce alla view i dati del player nel turno di partita in cui li ha richiesti:
        //Es: il suo punteggio; lo stato della sua bookshelf; recap common goal da completare/completati con relative goal tile; avanzamento del suo personal goal...
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o != view) {
            System.err.println("Discarding notification from " + o);
            return;
        }
        if(arg instanceof List && ((List<?>)arg).size()>0 && ((List<?>)arg).get(0) instanceof Tile) {
            List<Tile> selectedOrderedTiles = (List<Tile>) arg;
            addTilesToPlayerBookshelf(selectedOrderedTiles);
        } else {
            System.err.println("Discarding event from " + o + ": " + arg);
        }
    }
}
