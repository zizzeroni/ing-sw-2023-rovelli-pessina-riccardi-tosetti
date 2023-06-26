package it.polimi.ingsw.model.tile;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Enums all the possible colors that the {@code Tile} can assume
 * and provides methods to adjust color in CLI and GUI.
 */
public enum TileColor {
    GREEN, YELLOW, WHITE, BLUE, CYAN, PURPLE;

    /**
     * Redefines the toString method to incorporate colored text.
     *
     * @return the string used to represent the color associated to the Tile.
     *
     * @see Tile
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Ansi.Color color;

        switch (this) {
            case BLUE, GREEN, CYAN, YELLOW, WHITE -> color = Ansi.Color.valueOf(this.name());
            case PURPLE -> color = Ansi.Color.MAGENTA;
            default -> color = Ansi.Color.DEFAULT;
        }

        return stringBuilder.append(ansi().fg(color).a(this.name().charAt(0)).fg(Ansi.Color.DEFAULT)).toString();
    }

    /**
     * Defines a string associated to the colored text for the GUI's representation.
     *
     * @return the string used to represent the color text associated to the Tile.
     */
    public String toGUI() {
        return String.valueOf(this.name().charAt(0));
    }
}