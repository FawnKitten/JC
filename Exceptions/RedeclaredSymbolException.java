import java.lang.Exception;

public class RedeclaredSymbolException extends SymbolException {

    public RedeclaredSymbolException(String message) {
        super(message);
    }

    public RedeclaredSymbolException() {
        super();
    }

}
