package weatherwise.exception;

public class CityNotFoundException extends Exception{

    public CityNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
