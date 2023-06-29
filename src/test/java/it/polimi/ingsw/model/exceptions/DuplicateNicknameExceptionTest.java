package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.InPauseState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.OptionsValues;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DuplicateNicknameExceptionTest {

    private final String message = "Chosen nickname is already being utilized, please try another one!";
    private static final ByteArrayOutputStream errorContent = new ByteArrayOutputStream();
    private static final PrintStream originalErr = System.err;
    DuplicateNicknameException exception;

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
        exception = new DuplicateNicknameException(message);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that exception handling prints the error message in console")
    public void exception_handling_prints_error_message_in_console() {
        this.exception.handle();

        assertEquals(message + "\r\n", errorContent.toString());
        assertEquals(ExceptionType.DUPLICATE_NICKNAME_EXCEPTION, this.exception.toEnum());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that transformation of exception into enum is DUPLICATE_NICKNAME_EXCEPTION")
    public void exception_as_enum_value_is_duplicate_nickname_exception() {
        assertEquals(ExceptionType.DUPLICATE_NICKNAME_EXCEPTION, this.exception.toEnum());
    }
}
