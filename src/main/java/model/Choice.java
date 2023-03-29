package model;

import model.tile.Tile;

import java.util.List;

public class Choice {
    private List<Tile> chosenTiles;
    private int[] positions;
    private int chosenColumn;

    public Choice() {
        this.chosenTiles = null;
        this.positions = new int[3];
        for(int i=0;i<this.positions.length;i++) {
            positions[i]=0;
        }
        this.chosenColumn = 0;
    }

    public Choice(List<Tile> chosenTiles, int[] positions, int chosenColumn) {
        this.chosenTiles = chosenTiles;
        this.positions = positions;
        this.chosenColumn = chosenColumn;
    }

    public List<Tile> getChosenTiles() {
        return chosenTiles;
    }

    public void setChosenTiles(List<Tile> chosenTiles) {
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
}
