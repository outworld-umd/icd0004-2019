package weatherwise.exception;

public class CityIsEmptyException extends RuntimeException {
    public CityIsEmptyException(String s) {
        super(s);
    }
}
