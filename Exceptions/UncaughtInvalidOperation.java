package Exceptions;

public class UncaughtInvalidOperation extends InterpretException {

    public UncaughtInvalidOperation(String message) {
        super(message);
    }

    public UncaughtInvalidOperation() {
        super();
    }

}