package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * A record that implements the serialization of
 * the different type of messages.
 */
public record Message(MessageType messageType, String receiverNickname, String senderNickname,
                      String content) implements Serializable {


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[FROM] " + this.senderNickname + " [TO] ");

        switch (this.messageType) {
            case PRIVATE -> {
                stringBuilder.append(this.receiverNickname);
            }
            case BROADCAST -> {
                stringBuilder.append("ALL");
            }
        }
        stringBuilder.append(" : ").append(this.content);

        return stringBuilder.toString();
    }
}
