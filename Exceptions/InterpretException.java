package Exceptions;

import java.lang.Exception;

public class InterpretException extends Exception {

    public InterpretException(String message) {
        super(message);
    }

    public InterpretException() { super(); }

}
