package it.polimi.ingsw.model;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * An enumeration of the possible types of {@code Message}s
 * that the {@code Player} may send: {@code BROADCAST},
 * for messages readable by all other players or {@code PRIVATE},
 * for those sent to another specific chosen active {@code Player}.
 *
 * @see Message
 */
public enum MessageType {
    BROADCAST, PRIVATE;

    /**
     * Displays the type of {@code Message} being sent.
     *
     * @return a String to represent the message's type.
     * @see Message
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String name;
        switch (this) {
            case BROADCAST -> {
                name = "all";
            }
            case PRIVATE -> {
                name = "private";
            }
            default -> {
                name = "";
            }
        }

        return stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).a(name).fg(Ansi.Color.DEFAULT)).toString();
    }
}
