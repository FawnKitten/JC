package Exceptions;

public class RedeclaredSymbolException extends SymbolException {

    public RedeclaredSymbolException(String message) {
        super(message);
    }

    public RedeclaredSymbolException() {
        super();
    }

}
