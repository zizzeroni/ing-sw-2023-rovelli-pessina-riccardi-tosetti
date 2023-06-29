package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.ExceptionType;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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
        assertEquals("\u001B[33m[FROM] Marco\u001B[34m [TO] Andrea: \u001B[33mCiao", this.message.toString());

        this.message = new Message(MessageType.BROADCAST, "Marco", "Andrea", "CIAO");
        assertEquals("\u001B[34m[ALL] Andrea: \u001B[33mCIAO", this.message.toString());
    }
}
