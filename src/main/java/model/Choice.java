package model;

import model.view.TileView;
import utils.ObservableType;

import java.util.ArrayList;
import java.util.List;

public class Choice implements ObservableType {

    public static class Coord {
        private int x;
        private int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coord() {
            x = 0;
            y = 0;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public boolean equals(Coord obj) {
            return obj.getX()==this.getX() && obj.getY()==this.getY();
        }
    }

    private List<TileView> chosenTiles;
    private List<Coord> tileCoords;
    private int[] tileOrder;
    private int chosenColumn;

    public Choice() {
        this.chosenTiles = new ArrayList<>();
        this.tileOrder = new int[3];
        for (int i = 0; i < this.tileOrder.length; i++) {
            tileOrder[i] = 0;
        }
        this.chosenColumn = 0;
        tileCoords = new ArrayList<>();
    }

    public Choice(List<TileView> chosenTiles, List<Coord> tileCoords, int[] tileOrder, int chosenColumn) {
        this.chosenTiles = chosenTiles;
        this.tileOrder = tileOrder;
        this.chosenColumn = chosenColumn;
        this.tileCoords = tileCoords;
    }

    public List<Coord> getTileCoords() {
        return tileCoords;
    }

    public void setTileCoords(List<Coord> tileCoords) {
        this.tileCoords = tileCoords;
    }

    public List<TileView> getChosenTiles() {
        return chosenTiles;
    }

    public void setChosenTiles(List<TileView> chosenTiles) {
        this.chosenTiles = chosenTiles;
    }

    public int[] getTileOrder() {
        return tileOrder;
    }

    public void setTileOrder(int[] tileOrder) {
        this.tileOrder = tileOrder;
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

    public void addCoords(Coord coord) {
        this.tileCoords.add(coord);
    }

    @Override
    public Event getEvent() {
        return Event.USER_INPUT;
    }
}
