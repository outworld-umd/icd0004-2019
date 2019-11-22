package weatherwise.exception;

public class CurrentWeatherDataMissingException extends RuntimeException {
    public CurrentWeatherDataMissingException(String errorMessage) {
        super(errorMessage);
    }
}
