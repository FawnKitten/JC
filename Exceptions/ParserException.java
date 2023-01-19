package Exceptions;

import java.lang.Exception;

public class ParserException extends Exception {

    public ParserException(String message) {
        super(message);
    }

    public ParserException() {
        super();
    }

}
