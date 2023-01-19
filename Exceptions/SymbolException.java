package Exceptions;

import java.lang.Exception;

public class SymbolException extends Exception {

    public SymbolException(String message) {
        super(message);
    }

    public SymbolException() { super(); }

}
