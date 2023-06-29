package it.polimi.ingsw.model.exceptions;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExcessOfPlayersExceptionTest {

    private final String message = "The creator of the lobby restored a previous game that you weren't part of";
    private static final ByteArrayOutputStream errorContent = new ByteArrayOutputStream();
    ExcessOfPlayersException exception;

    @BeforeAll
    public static void setUpStreams() {
        System.setErr(new PrintStream(errorContent));
    }

    @AfterAll
    public static void restoreStreams() {
        System.setErr(null);
    }

    /**
     * Test class
     */
    @BeforeEach
    public void resetException() {
        exception = new ExcessOfPlayersException(message);
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
    @DisplayName("Test that transformation of exception into enum is EXCESS_OF_PLAYER_EXCEPTION")
    public void exception_as_enum_value_is_excess_of_player_exception() {
        assertEquals(ExceptionType.EXCESS_OF_PLAYERS_EXCEPTION, this.exception.toEnum());
    }
}
