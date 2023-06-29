package it.polimi.ingsw.model.exceptions;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WrongInputDataExceptionTest {

    private final String message = "[INPUT:ERROR]: User data not correct";
    private static final ByteArrayOutputStream errorContent = new ByteArrayOutputStream();
    WrongInputDataException exception;

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
        exception = new WrongInputDataException(message);
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
    @DisplayName("Test that transformation of exception into enum is WRONG_INPUT_DATA_EXCEPTION")
    public void exception_as_enum_value_is_wrong_input_data_exception() {
        assertEquals(ExceptionType.WRONG_INPUT_DATA_EXCEPTION, this.exception.toEnum());
    }
}
