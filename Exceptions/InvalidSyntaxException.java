import java.lang.Exception;

public class InvalidSyntaxException extends Exception {

    public InvalidSyntaxException(String message) {
        super(message);
    }

    public InvalidSyntaxException() {
        super();
    }

}
