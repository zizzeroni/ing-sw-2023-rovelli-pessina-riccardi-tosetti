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
        StringBuilder stringBuilder = new StringBuilder();

        switch (this.messageType) {
            case PRIVATE -> {
                stringBuilder.append("[PVT] ");
            }
            case BROADCAST -> {
                stringBuilder.append("[ALL] ");
            }
        }
        
        stringBuilder.append(this.senderNickname).append(": ").append(this.content);

        return stringBuilder.toString();
    }
}
