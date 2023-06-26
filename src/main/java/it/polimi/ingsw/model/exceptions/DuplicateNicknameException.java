package it.polimi.ingsw.model.exceptions;

/**
 * This class represents the DuplicateNicknameException.
 * It occurs when a {@code Player}'s nickname is duplicated.
 *
 * @see it.polimi.ingsw.model.Player
 */
public class DuplicateNicknameException extends GenericException {
    /**
     * Class constructor.
     * Initializes the exception used to handle the occurrence of a {code Player}'s nickname duplication.
     *
     * @param message is the message to signal nickname's duplication.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public DuplicateNicknameException(String message) {
        super(message);
    }

    /**
     * Used to enumerate the type of the class exception.
     *
     * @return the {@code ExceptionType} of the nickname's duplication exception.
     *
     * @see ExceptionType
     */
    public ExceptionType toEnum() {
        return ExceptionType.DUPLICATE_NICKNAME_EXCEPTION;
    }

    /**
     * Handler of the message linked to the {@code Player}'s nickname's duplication exception.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public void handle() {
        System.err.println(this.getMessage());
    }
}
