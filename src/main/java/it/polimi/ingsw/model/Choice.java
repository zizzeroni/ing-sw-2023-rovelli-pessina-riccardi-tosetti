package it.polimi.ingsw.model;

import it.polimi.ingsw.model.view.TileView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Choice implements Serializable {
    private List<TileView> chosenTiles;
    private List<Coordinates> tileCoordinates;
    private int[] tileOrder;
    private int chosenColumn;

    public Choice() {
        this.chosenTiles = new ArrayList<>();
        this.tileOrder = new int[3];
        for (int i = 0; i < this.tileOrder.length; i++) {
            tileOrder[i] = 0;
        }
        this.chosenColumn = 0;
        this.tileCoordinates = new ArrayList<>();
    }

    /*
    @params chosenTiles are the tiles chosen by the player
    @params tileCoordinates are the coordinates of the tiles chosen by the player
    @params tileOrder is the order in which the player want to insert the tiles in his bookshelf
     */
    public Choice(List<TileView> chosenTiles, List<Coordinates> tileCoordinates, int[] tileOrder, int chosenColumn) {
        this.chosenTiles = chosenTiles;
        this.tileOrder = tileOrder;
        this.chosenColumn = chosenColumn;
        this.tileCoordinates = tileCoordinates;
    }

    public List<Coordinates> getTileCoordinates() {
        return this.tileCoordinates;
    }

    public void setTileCoordinates(List<Coordinates> tileCoordinates) {
        this.tileCoordinates = tileCoordinates;
    }

    public List<TileView> getChosenTiles() {
        return this.chosenTiles;
    }

    public void setChosenTiles(List<TileView> chosenTiles) {
        this.chosenTiles = chosenTiles;
    }

    public int[] getTileOrder() {
        return this.tileOrder;
    }

    public void setTileOrder(int[] tileOrder) {
        this.tileOrder = tileOrder;
    }

    public int getChosenColumn() {
        return this.chosenColumn;
    }

    public void setChosenColumn(int chosenColumn) {
        this.chosenColumn = chosenColumn;
    }

    public void addTile(TileView tile) {
        this.chosenTiles.add(tile);
    }

    public void addCoordinates(Coordinates coordinates) {
        this.tileCoordinates.add(coordinates);
    }

}
