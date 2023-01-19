package Exceptions;

public class InvalidCharacterException extends ParserException {

    public InvalidCharacterException(String message) {
        super(message);
    }

    public InvalidCharacterException() {
        super();
    }
}
