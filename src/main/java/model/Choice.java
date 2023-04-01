package model;

import model.tile.Tile;
import model.view.TileView;
import utils.ObservableType;

import java.util.ArrayList;
import java.util.List;

public class Choice implements ObservableType {
    private List<TileView> chosenTiles;
    private int[] positions;
    private int chosenColumn;

    public Choice() {
        this.chosenTiles = new ArrayList<>();
        this.positions = new int[3];
        for(int i=0;i<this.positions.length;i++) {
            positions[i]=0;
        }
        this.chosenColumn = 0;
    }

    public Choice(List<TileView> chosenTiles, int[] positions, int chosenColumn) {
        this.chosenTiles = chosenTiles;
        this.positions = positions;
        this.chosenColumn = chosenColumn;
    }

    public List<TileView> getChosenTiles() {
        return chosenTiles;
    }

    public void setChosenTiles(List<TileView> chosenTiles) {
        this.chosenTiles = chosenTiles;
    }

    public int[] getPositions() {
        return positions;
    }

    public void setPositions(int[] positions) {
        this.positions = positions;
    }

    public int getChosenColumn() {
        return chosenColumn;
    }

    public void setChosenColumn(int chosenColumn) {
        this.chosenColumn = chosenColumn;
    }

    public void addTile(TileView tile) {
        this.chosenTiles.add(tile);
    }
    @Override
    public Event getEvent() {
        return Event.USER_INPUT;
    }
}
