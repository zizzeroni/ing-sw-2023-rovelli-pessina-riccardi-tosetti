package it.polimi.ingsw.model.exceptions;

public class ExcessOfPlayersException extends GenericException {
    public ExcessOfPlayersException(String message) {
        super(message);
    }

    public ExceptionType toEnum() {
        return ExceptionType.EXCESS_OF_PLAYER_EXCEPTION;
    }

    public void handle() {
        System.err.println(this.getMessage());
        System.exit(1);
    }
}
