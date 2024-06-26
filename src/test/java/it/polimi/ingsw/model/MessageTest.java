package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTest {

    private Message message;

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that message is formatted correctly for given sender, receiver, type and content")
    public void message_is_formatted_correctly() {
        this.message = new Message(MessageType.PRIVATE, "Andrea", "Marco", "Ciao");
        assertEquals("[FROM] Marco [TO] Andrea: Ciao", this.message.toString());

        this.message = new Message(MessageType.BROADCAST, "Marco", "Andrea", "CIAO");
        assertEquals("[ALL] Andrea: CIAO", this.message.toString());
    }
}
