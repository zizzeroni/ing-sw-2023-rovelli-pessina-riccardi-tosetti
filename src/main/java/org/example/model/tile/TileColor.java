package org.example.model.tile;

public enum TileColor {
    GREEN, YELLOW, WHITE, BLUE, CYAN, PURPLE;

    @Override
    public String toString() {
        return this.name().charAt(0) + "";
    }
}