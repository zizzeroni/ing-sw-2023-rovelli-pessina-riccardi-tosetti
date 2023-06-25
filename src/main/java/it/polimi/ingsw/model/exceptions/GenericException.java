package it.polimi.ingsw.model.exceptions;

public class GenericException extends Exception {
    public GenericException(String message) {
        super(message);
    }

    public ExceptionType toEnum() {
        return ExceptionType.GENERIC_EXCEPTION;
    }

    public void handle() {
        System.err.println(this.getMessage());
    }
}
