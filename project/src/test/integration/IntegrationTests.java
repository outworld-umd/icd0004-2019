package integration;

import com.sun.jersey.api.client.ClientResponse;
import org.junit.BeforeClass;
import org.junit.Test;
import weatherwise.api.WeatherApi;
import weatherwise.api.response.CurrentWeatherData;
import weatherwise.api.response.ForecastData;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class IntegrationTests {

    private static WeatherApi weatherApi;

    @BeforeClass
    public static void setUp() {
        weatherApi = new WeatherApi();
    }

    @Test
    public void shouldReturnCode200IfCurrentWeatherReportSuccessful() {
        String city = "Tallinn";

        ClientResponse response = weatherApi.getCurrentWeatherResponse(city);

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void shouldReturnCurrentWeatherReportCode404IfNoCity() {
        String city = "Reval";

        ClientResponse response = weatherApi.getCurrentWeatherResponse(city);

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void shouldReturnCode200IfForecastReportSuccessful() {
        String city = "Tallinn";

        ClientResponse response = weatherApi.getForecastResponse(city);

        assertThat(response.getStatus(), is(200));
    }

    @Test
    public void shouldReturnForecastReportCode404IfNoCity() {
        String city = "Reval";

        ClientResponse response = weatherApi.getForecastResponse(city);

        assertThat(response.getStatus(), is(404));
    }

    @Test
    public void temperatureShouldBeNonNullIfSuccessful() {
        String city = "Tallinn";

        ForecastData forecastData = weatherApi.getForecastDataForCity(city);
        Double temperature = forecastData.getList().get(0).getMain().getTemp();

        assertThat(temperature, is(notNullValue()));
    }

    @Test
    public void humidityShouldBeNonNullIfSuccessful() {
        String city = "Tallinn";

        ForecastData forecastData = weatherApi.getForecastDataForCity(city);
        Integer humidity = forecastData.getList().get(0).getMain().getHumidity();

        assertThat(humidity, is(notNullValue()));
    }

    @Test
    public void pressureShouldBeNonNullIfSuccessful() {
        String city = "Tallinn";

        ForecastData forecastData = weatherApi.getForecastDataForCity(city);
        Integer pressure = forecastData.getList().get(0).getMain().getPressure();

        assertThat(pressure, is(notNullValue()));
    }

}
