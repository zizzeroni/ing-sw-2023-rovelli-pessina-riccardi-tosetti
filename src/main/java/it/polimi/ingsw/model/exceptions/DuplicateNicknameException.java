package it.polimi.ingsw.model.exceptions;

public class DuplicateNicknameException extends GenericException {
    public DuplicateNicknameException(String message) {
        super(message);
    }

    public ExceptionType toEnum() {
        return ExceptionType.DUPLICATE_NICKNAME_EXCEPTION;
    }

    public void handle() {
        System.err.println(this.getMessage());
    }
}
