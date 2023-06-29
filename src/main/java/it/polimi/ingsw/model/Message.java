package it.polimi.ingsw.model;

import org.fusesource.jansi.Ansi;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * A record that permits the implementation of the serialization
 * of the different types of messages.
 *
 * @param messageType      type of the message (private or broadcast).
 * @param receiverNickname nickname of the receiver.
 * @param senderNickname   nickname of the sender
 * @param content          content of the message
 */
public record Message(MessageType messageType, String receiverNickname, String senderNickname,
                      String content) implements Serializable {


    /**
     * This method is used to identify the String to be sent as message, while setting all its parameters.
     *
     * @return the String message to be sent.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        switch (this.messageType) {
            case PRIVATE -> {
                stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).a("[FROM] " + this.senderNickname)).append(ansi().fg(Ansi.Color.BLUE).a(" [TO] " + this.receiverNickname + ": "));
            }
            case BROADCAST -> {
                stringBuilder.append(ansi().fg(Ansi.Color.BLUE).a("[ALL] " + this.senderNickname + ": "));
            }
        }

        stringBuilder.append(ansi().fg(Ansi.Color.YELLOW).a(this.content));

        return stringBuilder.toString();
    }
}
