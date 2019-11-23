package weatherwise.exception;

public class ForecastWeatherDataMissingException extends RuntimeException {
    public ForecastWeatherDataMissingException(String errorMessage) {
        super(errorMessage);
    }
}
