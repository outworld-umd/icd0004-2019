package stub;

import weatherwise.WeatherReport;
import weatherwise.WeatherWise;
import weatherwise.api.WeatherApi;
import weatherwise.api.response.CurrentWeatherData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class WeatherWiseMockTest {

    @Mock
    WeatherApi weatherApiMock;

    private WeatherWise weatherWise;

    @Before
    public void setUp() {
        weatherWise = new WeatherWise(weatherApiMock);
    }

    // Example of stubbing
    @Test
    public void should_return_weather_report_for_city() {
        String city = "Tallinn";
        CurrentWeatherData currentWeatherData = new CurrentWeatherData();
        currentWeatherData.setName(city);
        // When we ask the api for current weather data using some string then always return the same data
        // In other words we forget about the logic of WeatherApi and focus on WeatherWise and what it should do
        // with the data the WeatherApi provides.
        Mockito.when(weatherApiMock.getCurrentWeatherDataForCity(anyString())).thenReturn(currentWeatherData);

        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);

        assertEquals(weatherReport.getWeatherReportDetails().getCity(), city);
    }


}