package it.polimi.ingsw.model;

public record Message(MessageType messageType, String receiverNickname, String senderNickname, String content) {

    @Override
    public String toString() {
        return "[" + this.messageType + "] " + this.senderNickname + ": " + this.content;
    }
}
