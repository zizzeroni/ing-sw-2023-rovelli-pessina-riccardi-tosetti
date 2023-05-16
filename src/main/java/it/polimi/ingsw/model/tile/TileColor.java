package it.polimi.ingsw.model.tile;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public enum TileColor {
    GREEN, YELLOW, WHITE, BLUE, CYAN, PURPLE;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Ansi.Color color;

        switch (this) {
            case BLUE, GREEN, CYAN, YELLOW, WHITE -> color = Ansi.Color.valueOf(String.valueOf(this.name()));
            case PURPLE -> color = Ansi.Color.MAGENTA;
            default -> color = Ansi.Color.DEFAULT;
        }

        return stringBuilder.append(ansi().fg(color).a(this.name().charAt(0)).fg(Ansi.Color.DEFAULT)).toString();
    }
}