package Exceptions;

public class MismatchingArgumentsException extends SymbolException {

    public MismatchingArgumentsException(String message) {
        super(message);
    }

    public MismatchingArgumentsException() {
        super();
    }
}
