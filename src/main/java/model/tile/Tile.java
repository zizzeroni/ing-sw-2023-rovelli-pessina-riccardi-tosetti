package model.tile;

public class Tile {
    private int x;
    private int y;
    private TileColor color;
    private int score;

    public Tile() {
    }
    public Tile(TileColor color) {
        this.color = color;
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

    public TileColor getColor() {
        return color;
    }
    public void setColor(TileColor color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void setScore(String image) {
        this.score = score;
    }
}
