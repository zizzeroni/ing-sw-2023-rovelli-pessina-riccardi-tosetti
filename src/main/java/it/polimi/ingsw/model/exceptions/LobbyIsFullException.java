package it.polimi.ingsw.model.exceptions;

public class LobbyIsFullException extends GenericException {
    public LobbyIsFullException(String message) {
        super(message);
    }

    public ExceptionType toEnum() {
        return ExceptionType.LOBBY_IS_FULL_EXCEPTION;
    }

    public void handle() {
        System.err.println(this.getMessage());
    }
}
