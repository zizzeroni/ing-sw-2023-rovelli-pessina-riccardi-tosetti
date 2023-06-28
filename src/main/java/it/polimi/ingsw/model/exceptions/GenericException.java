package it.polimi.ingsw.model.exceptions;

/**
 * This class is used to represent the GENERIC {code ExceptionType}.
 *
 * @see ExceptionType#GENERIC_EXCEPTION
 */
public class GenericException extends Exception {

    /**
     * Class constructor.
     * Initializes the exception used to handle the occurrence of a generic exception.
     *
     * @param message is the message to signal a generic exception.
     * @see it.polimi.ingsw.model.Player
     */
    public GenericException(String message) {
        super(message);
    }

    /**
     * Used to enumerate the type of the class exception.
     *
     * @return the {@code ExceptionType} of the generic exception.
     * @see ExceptionType
     */
    public ExceptionType toEnum() {
        return ExceptionType.GENERIC_EXCEPTION;
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
