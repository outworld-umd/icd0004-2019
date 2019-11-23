package weatherwise.exception;

public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
