package it.polimi.ingsw.model.exceptions;
/**
 * This class represents the exception occurring when the current's game lobby is considered as full and some other player tries to join it.
 *
 * @see it.polimi.ingsw.model.Game
 */
public class LobbyIsFullException extends GenericException {
    /**
     * Class constructor.
     * Initialize the class parameters.
     *
     * @param message the message displayed by the class exception.
     */
    public LobbyIsFullException(String message) {
        super(message);
    }

    /**
     * Enumerates the class exception.
     *
     * @return the ExceptionType of the class exception.
     */
    public ExceptionType toEnum() {
        return ExceptionType.LOBBY_IS_FULL_EXCEPTION;
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
