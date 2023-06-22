package it.polimi.ingsw.model.tile;

public class Tile {
    private TileColor color;
    private int id;

    public Tile() {
        this.color = null;
        this.id = 0;
    }

    public Tile(TileColor color, int id) {
        this.color = color;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public Tile(TileColor color) {
        this.color = color;
    }

    public TileColor getColor() {
        return this.color;
    }

    public void setColor(TileColor color) {
        this.color = color;
    }
}
