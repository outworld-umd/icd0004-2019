package integration;

import org.junit.BeforeClass;
import org.junit.Test;
import weatherwise.api.WeatherApi;
import weatherwise.api.response.CurrentWeatherData;
import weatherwise.api.response.ForecastData;

import static org.junit.Assert.*;

public class IntegrationTests {

    private static WeatherApi weatherApi;

    @BeforeClass
    public static void setUp() {
        weatherApi = new WeatherApi();
    }

    @Test
    public void shouldReturnCode200IfSuccessful() {
        String city = "Tallinn";

        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);
        int code = currentWeatherData.getCod();

        assertEquals(200, code);
    }

    @Test
    public void shouldReturnNullMessageIfSuccessful() {
        String city = "Tallinn";

        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);
        String message = currentWeatherData.getMessage();

        assertNull(message);
    }

    @Test
    public void shouldReturnCode404IfNoCity() {
        String city = "Reval";

        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);
        int code = currentWeatherData.getCod();

        assertEquals(404, code);
    }

    @Test
    public void shouldReturnNoCityMessageIfNotFound() {
        String city = "Reval";

        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);
        String message = currentWeatherData.getMessage();

        assertEquals("city not found", message);
    }

    @Test
    public void temperatureShouldBeNonNullIfSuccessful() {
        String city = "Tallinn";

        ForecastData forecastData = weatherApi.getForecastDataForCity(city);
        Double temperature = forecastData.getList().get(0).getMain().getTemp();

        assertNotNull(temperature);
    }

    @Test
    public void humidityShouldBeNonNullIfSuccessful() {
        String city = "Tallinn";

        ForecastData forecastData = weatherApi.getForecastDataForCity(city);
        Integer humidity = forecastData.getList().get(0).getMain().getHumidity();

        assertNotNull(humidity);
    }

    @Test
    public void pressureShouldBeNonNullIfSuccessful() {
        String city = "Tallinn";

        ForecastData forecastData = weatherApi.getForecastDataForCity(city);
        Integer pressure = forecastData.getList().get(0).getMain().getPressure();

        assertNotNull(pressure);
    }

}
