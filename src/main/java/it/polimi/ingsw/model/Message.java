package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * A record that permits the implementation of the serialization
 * of the different types of messages.
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
