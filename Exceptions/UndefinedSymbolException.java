package Exceptions;

public class UndefinedSymbolException extends SymbolException {
    public UndefinedSymbolException(String message) {
        super(message);
    }

    public UndefinedSymbolException() {
        super();
    }
}
