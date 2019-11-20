package weatherwise.exception;

public class CurrentWeatherDataMissingException extends Exception {
    public CurrentWeatherDataMissingException(String errorMessage) {
        super(errorMessage);
    }
}
