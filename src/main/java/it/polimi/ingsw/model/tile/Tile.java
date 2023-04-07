package it.polimi.ingsw.model.tile;

public class Tile {
    private TileColor color;
    private int imageID;
    public Tile() {
        this.color = null;
        this.imageID = 0;
    }
    public Tile(TileColor color, int imageID) {
        this.color = color;
        this.imageID = imageID;
    }
    public int getImageID() { return imageID; }
    public void setImageID(int imageID) { this.imageID = imageID; }
    public Tile(TileColor color) { this.color = color; }
    public TileColor getColor() {
        return color;
    }
    public void setColor(TileColor color) {
        this.color = color;
    }
}
