package it.polimi.ingsw.model.exceptions;

/**
 * This class represents the exception occurring when the data received as input present errors of any sort.
 *
 * @see it.polimi.ingsw.model.Game
 */
public class WrongInputDataException extends GenericException {
    /**
     * Class constructor.
     * Initialize the class parameters.
     *
     * @param message the message displayed by the class exception.
     */
    public WrongInputDataException(String message) {
        super(message);
    }
    /**
     * Enumerates the class exception.
     *
     * @return the ExceptionType of the class exception.
     */
    public ExceptionType toEnum() {
        return ExceptionType.WRONG_INPUT_DATA_EXCEPTION;
    }

    /**
     * Handler of the message linked to the generic exception.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public void handle() {
        System.err.println(this.getMessage());
    }
}
