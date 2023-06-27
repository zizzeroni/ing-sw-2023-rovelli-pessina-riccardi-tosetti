package it.polimi.ingsw.model.exceptions;

/**
 * This class represents the exception occurring when there is a number of player exceeding the game's set numberOfPLayers.
 *
 * @see it.polimi.ingsw.model.Game
 */
public class ExcessOfPlayersException extends GenericException {
    /**
     * Class constructor.
     * Initialize the class parameters.
     *
     * @param message the message displayed by the class exception.
     */
    public ExcessOfPlayersException(String message) {
        super(message);
    }

    /**
     * Enumerates the class exception.
     *
     * @return the ExceptionType of the class exception.
     */
    public ExceptionType toEnum() {
        return ExceptionType.EXCESS_OF_PLAYER_EXCEPTION;
    }

    /**
     * Handler of the message linked to the generic exception.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public void handle() {
        System.err.println(this.getMessage());
        System.exit(1);
    }
}
