package it.polimi.ingsw.model;

import it.polimi.ingsw.model.view.TileView;
import it.polimi.ingsw.utils.OptionsValues;

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
 *
 * @see Player
 * @see Board
 * @see Game
 */
public class Choice implements Serializable {
    private List<TileView> chosenTiles;
    private List<Coordinates> tileCoordinates;
    private int[] tileOrder;
    private int chosenColumn;

    /**
     * Class Builder. Creates the object Choice with the initial unset values of lists for chosenTile/tileCoordinates, the tileOrder array, ecc.
     */
    public Choice() {
        this.chosenTiles = new ArrayList<>();
        this.tileOrder = new int[OptionsValues.MAX_NUMBER_PICKABLE_TILES];
        for (int i = 0; i < this.tileOrder.length; i++) {
            tileOrder[i] = 0;
        }
        this.chosenColumn = 0;
        this.tileCoordinates = new ArrayList<>();
    }

    /**
     * Class Builder. Creates the object Choice with the values of lists for chosenTile/tileCoordinates, the tileOrder array, ecc.
     *
     * @params chosenTiles are the tiles chosen by the player
     * @params tileCoordinates are the coordinates of the tiles chosen by the player
     * @params tileOrder is the order in which the player want to insert the tiles in his bookshelf
     */
    public Choice(List<TileView> chosenTiles, List<Coordinates> tileCoordinates, int[] tileOrder, int chosenColumn) {
        this.chosenTiles = chosenTiles;
        this.tileOrder = tileOrder;
        this.chosenColumn = chosenColumn;
        this.tileCoordinates = tileCoordinates;
    }

    /**
     * Gets the list of the coordinates of the {@code Tile}s employed in the {@code Choice}.
     *
     * @return the list of tiles' coordinates.
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public List<Coordinates> getTileCoordinates() {
        return this.tileCoordinates;
    }

    /**
     * sets the list of the coordinates of the {@code Tile}s employed in the {@code Choice}.
     *
     * @param tileCoordinates the coordinates of the tiles to be inserted in the list.
     * @return the list of tiles' coordinates.
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public void setTileCoordinates(List<Coordinates> tileCoordinates) {
        this.tileCoordinates = tileCoordinates;
    }

    public List<TileView> getChosenTiles() {
        return this.chosenTiles;
    }

    /**
     * Sets the list of {@code Tile}s chosen by the {@code Player}.
     *
     * @return chosenTiles is the list of selected tiles.
     * @see it.polimi.ingsw.model.tile.Tile
     * @see Player
     */
    public void setChosenTiles(List<TileView> chosenTiles) {
        this.chosenTiles = chosenTiles;
    }

    /**
     * Gets the selected {@code Tile}s order during the player turn.
     *
     * @return the {@code tileOrder} given from the {@code Player} to the tiles' list.
     * @see it.polimi.ingsw.model.tile.Tile
     * @see Player
     */
    public int[] getTileOrder() {
        return this.tileOrder;
    }

    /**
     * Sets the selected {@code Tile}s order during the player's turn.
     *
     * @param tileOrder given from the {@code Player} to the tiles' list.
     * @see it.polimi.ingsw.model.tile.Tile
     * @see Player
     */
    public void setTileOrder(int[] tileOrder) {
        this.tileOrder = tileOrder;
    }

    /**
     * Gets the index of the column chosen by the {@code Player}.
     *
     * @return the chosen column.
     * @see Player
     */
    public int getChosenColumn() {
        return this.chosenColumn;
    }

    /**
     * Sets the chosen column at the given value.
     *
     * @param chosenColumn is the column chosen by the {@code Player}
     * @see Player
     */
    public void setChosenColumn(int chosenColumn) {
        this.chosenColumn = chosenColumn;
    }

    /**
     * Method for tiles selection during the {@code Player} turn.
     *
     * @param tile the {@code Tile} to be added in the {@code chosenTiles} list.
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public void addTile(TileView tile) {
        this.chosenTiles.add(tile);
    }

    /**
     * Method for tiles removal employed during the {@code Player} turn.
     * The selected {@code Tile} is removed from its actual position.
     *
     * @param tile the tile to be removed from the {@code chosenTiles} list.
     * @see it.polimi.ingsw.model.tile.Tile
     * @see Player
     */
    public void removeTile(TileView tile) {
        int pos = this.chosenTiles.indexOf(tile);
        this.chosenTiles.remove(tile);
        this.tileCoordinates.remove(pos);
    }

    /**
     * Adds {@code Tile}s coordinates to the relative {@code tileCoordinates} list.
     *
     * @param coordinates are the coordinates to be inserted in their reference list.
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public void addCoordinates(Coordinates coordinates) {
        this.tileCoordinates.add(coordinates);
    }

}
