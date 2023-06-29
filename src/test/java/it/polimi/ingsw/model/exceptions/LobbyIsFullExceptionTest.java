package it.polimi.ingsw.model.exceptions;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LobbyIsFullExceptionTest {

    private final String message = "Cannot access a game: Lobby is full or you were not part of it at the start of the game";
    private static final ByteArrayOutputStream errorContent = new ByteArrayOutputStream();
    private static final PrintStream originalErr = System.err;
    LobbyIsFullException exception;

    @BeforeAll
    public static void setUpStreams() {
        System.setErr(new PrintStream(errorContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setErr(originalErr);
    }

    /**
     * Test class
     */
    @BeforeEach
    public void resetException() {
        exception = new LobbyIsFullException(message);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that exception handling prints the error message in console")
    public void exception_handling_prints_error_message_in_console() {
        this.exception.handle();

        assertEquals(message + "\r\n", errorContent.toString());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that transformation of exception into enum is LOBBY_IS_FULL_EXCEPTION")
    public void exception_as_enum_value_is_lobby_is_full_exception() {
        assertEquals(ExceptionType.LOBBY_IS_FULL_EXCEPTION, this.exception.toEnum());
    }
}
