package it.polimi.ingsw.model;

import it.polimi.ingsw.model.view.TileView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an object Choice that permits
 * the {@code Player} to implements actions during the running
 * of the {@code Game} which he is participating.
 * <p>
 * The representation of the actual choice is given through the
 * four attributes listed in the class.
 * The {@code Player} can operate by choosing Tiles using as a reference
 * their coordinates and select their order before their insertion on the {@code Board}.
 */
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

    /**
     * Class Builder. Creates the object Choice with the initial unset values of lists for chosenTile/tileCoordinates, the tileOrder array, ecc.
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

    /**
     * Sets the list of tiles chosen by the {@code Player}.
     *
     * @return chosenTiles is the list of selected tiles.
     */
    public void setChosenTiles(List<TileView> chosenTiles) {
        this.chosenTiles = chosenTiles;
    }

    /**
     * Gets the selected tiles order during the player turn.
     *
     * @return the {@code tileOrder} given from the {@code Player} to the tiles' list.
     */
    public int[] getTileOrder() {
        return this.tileOrder;
    }

    /**
     * Sets the selected tiles order during the player turn.
     *
     * @param tileOrder given from the {@code Player} to the tiles' list.
     */
    public void setTileOrder(int[] tileOrder) {
        this.tileOrder = tileOrder;
    }

    public int getChosenColumn() {
        return this.chosenColumn;
    }

    public void setChosenColumn(int chosenColumn) {
        this.chosenColumn = chosenColumn;
    }

    /**
     * Method for tiles selection during the {@code Player} turn.
     *
     * @param tile the tile to be added in the {@code chosenTiles} list.
     */
    public void addTile(TileView tile) {
        this.chosenTiles.add(tile);
    }

    /**
     * Method for tiles removal during the {@code Player} turn.
     * The selected {@code Tile} is removed from its actual position.
     *
     * @param tile the tile to be removed from the {@code chosenTiles} list.
     */
    public void removeTile(TileView tile) {
        int pos = this.chosenTiles.indexOf(tile);
        this.chosenTiles.remove(tile);
        this.tileCoordinates.remove(pos);
    }

    /**
     * Add tiles coordinates to the relative {@code tileCoordinates} list.
     *
     * @param coordinates are the coordinates to be inserted in their reference list.
     */
    public void addCoordinates(Coordinates coordinates) {
        this.tileCoordinates.add(coordinates);
    }

}
