package weatherwise.exception;

public class CitiesFileNotFoundException extends RuntimeException {
    public CitiesFileNotFoundException(String message) {
        super(message);
    }
}
