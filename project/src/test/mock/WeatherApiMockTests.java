package mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import weatherwise.WeatherFile;
import weatherwise.WeatherWise;
import weatherwise.api.WeatherApi;
import weatherwise.api.response.CurrentWeatherData;
import weatherwise.exception.CityIsEmptyException;
import weatherwise.exception.CurrentWeatherDataMissingException;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherApiMockTests {

    @Mock
    WeatherApi weatherApiMock;

    @Mock
    WeatherFile weatherFileMock;

    private WeatherWise weatherWise;

    @Before
    public void setUp() {
        weatherWise = new WeatherWise(weatherApiMock);
    }

    @Test
    public void shouldNotCallApiWhenCityIsEmpty() {
        try {
            weatherWise.getWeatherReportForCity(null);
        } catch (CityIsEmptyException ignored) {
        }

        verifyZeroInteractions(weatherApiMock);
    }

    @Test
    public void shouldCallApiWhenCityNameIsProvided() {
        String city = "Tallinn";
        when(weatherApiMock.getCurrentWeatherDataForCity(anyString()))
                .thenReturn(mock(CurrentWeatherData.class));

        try {
            weatherWise.getWeatherReportForCity(city);
        } catch (CurrentWeatherDataMissingException ignored) {
        }

        verify(weatherApiMock).getCurrentWeatherDataForCity(city);
    }

    @Test
    public void shouldCallApiHowManyCitiesThereAreInFile() {
        WeatherWise weatherWise = new WeatherWise(weatherApiMock, weatherFileMock);
        List<String> citiesList = List.of("Tallinn", "Not valid", "Maybe valid? No", "Helsinki", "  Mkm  ");
        Mockito.when(weatherFileMock.getCitiesFromFile(anyString())).thenReturn(citiesList);

        weatherWise.getWeatherReportFromFile(anyString());

        verify(weatherApiMock, times(citiesList.size())).getCurrentWeatherDataForCity(anyString());
    }

    @Test
    public void shouldNotCallApiIfNoCitiesInFile() {
        WeatherWise weatherWise = new WeatherWise(weatherApiMock, weatherFileMock);
        List<String> citiesList = List.of("");
        Mockito.when(weatherFileMock.getCitiesFromFile(anyString())).thenReturn(citiesList);

        weatherWise.getWeatherReportFromFile(anyString());

        verifyZeroInteractions(weatherApiMock);
    }

    @Test
    public void shouldNotInvokeForecastDataGetterForNonValidCity() {
        WeatherWise weatherWise = new WeatherWise(weatherApiMock, weatherFileMock);
        List<String> citiesList = List.of("This city is not valid");
        Mockito.when(weatherFileMock.getCitiesFromFile(anyString())).thenReturn(citiesList);

        weatherWise.getWeatherReportFromFile(anyString());

        verify(weatherApiMock, times(0)).getForecastDataForCity(anyString());
    }
}
