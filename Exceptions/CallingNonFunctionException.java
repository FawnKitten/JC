package Exceptions;

public class CallingNonFunctionException extends SymbolException {

    public CallingNonFunctionException(String message) {
        super(message);
    }

    public CallingNonFunctionException() {
        super();
    }

}