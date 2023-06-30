package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.TileView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChoiceTest {
    private Choice choice;

    private Bookshelf bookshelf;

    @BeforeEach
    public void cleanGoal() {
        choice = new Choice();
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that choice's utility and accessories methods work correctly")
    public void utility_and_acchessories_methods_work_correctly() {

        List<Coordinates> coordinates = new ArrayList<>(Arrays.asList(new Coordinates(0, 4), new Coordinates(3, 2)));
        this.choice.setTileCoordinates(coordinates);
        for (int i = 0; i < coordinates.size(); i++) {
            assertEquals(coordinates.get(i).getX(), this.choice.getTileCoordinates().get(i).getX());
            assertEquals(coordinates.get(i).getY(), this.choice.getTileCoordinates().get(i).getY());
            assertTrue(coordinates.get(i).equals(this.choice.getTileCoordinates().get(i)));
        }

        List<TileView> chosenTiles = new ArrayList<>(Arrays.asList(new TileView(new Tile(TileColor.GREEN, 2)), new TileView(new Tile(TileColor.PURPLE, 3))));
        this.choice.setChosenTiles(chosenTiles);
        for (int i = 0; i < chosenTiles.size(); i++) {
            assertEquals(chosenTiles.get(i).getColor(), this.choice.getChosenTiles().get(i).getColor());
            assertEquals(chosenTiles.get(i).getId(), this.choice.getChosenTiles().get(i).getId());
            assertEquals(chosenTiles.get(i), this.choice.getChosenTiles().get(i));
        }

        int[] tileOrder = new int[]{2, 1};
        this.choice.setTileOrder(tileOrder);
        for (int i = 0; i < tileOrder.length; i++) {
            assertEquals(tileOrder[i], this.choice.getTileOrder()[i]);
        }

        this.choice.setChosenColumn(2);
        assertEquals(2, this.choice.getChosenColumn());

        this.choice.addTile(new TileView(new Tile(TileColor.BLUE, 1)));
        assertEquals(1, this.choice.getChosenTiles().get(this.choice.getChosenTiles().size() - 1).getId());
        assertEquals(TileColor.BLUE, this.choice.getChosenTiles().get(this.choice.getChosenTiles().size() - 1).getColor());

        Coordinates coordinate = new Coordinates();
        coordinate.setX(1);
        coordinate.setY(1);

        this.choice.addCoordinates(coordinate);
        assertEquals(1, this.choice.getTileCoordinates().get(this.choice.getChosenTiles().size() - 1).getX());
        assertEquals(1, this.choice.getTileCoordinates().get(this.choice.getChosenTiles().size() - 1).getY());

    }
}
