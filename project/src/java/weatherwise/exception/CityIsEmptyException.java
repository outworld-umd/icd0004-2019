package weatherwise.exception;

public class CityIsEmptyException extends Exception {
    public CityIsEmptyException(String s) {
        super(s);
    }
}
