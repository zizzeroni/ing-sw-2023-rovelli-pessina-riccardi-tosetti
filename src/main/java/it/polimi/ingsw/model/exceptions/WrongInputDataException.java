package it.polimi.ingsw.model.exceptions;

public class WrongInputDataException extends GenericException {
    public WrongInputDataException(String message) {
        super(message);
    }

    public ExceptionType toEnum() {
        return ExceptionType.WRONG_INPUT_DATA_EXCEPTION;
    }

    public void handle() {
        System.err.println(this.getMessage());
    }
}
