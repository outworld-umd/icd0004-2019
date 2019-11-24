package mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import weatherwise.WeatherFile;
import weatherwise.WeatherReport;
import weatherwise.WeatherWise;
import weatherwise.api.WeatherApi;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeatherFileMockTest {

    @Mock
    WeatherFile weatherFileMock;

    private WeatherWise weatherWise;

    @Before
    public void setUp() {
        weatherWise = new WeatherWise(new WeatherApi(), weatherFileMock);
    }

    @Test
    public void shouldReturnEmptyListIfNoCities() {
        List<String> citiesList = new ArrayList<>();
        Mockito.when(weatherFileMock.getCitiesFromFile(anyString())).thenReturn(citiesList);

        List<WeatherReport> reports = weatherWise.getWeatherReportFromFile(anyString());

        assertEquals(0, reports.size());
    }

    @Test
    public void shouldReturnThreeWeatherReportsIfThreeCities() {
        List<String> citiesList = List.of("Tallinn", "Tartu", "Pärnu");
        Mockito.when(weatherFileMock.getCitiesFromFile(anyString())).thenReturn(citiesList);

        List<WeatherReport> reports = weatherWise.getWeatherReportFromFile(anyString());

        assertEquals(3, reports.size());
    }

    @Test
    public void shouldReturnSomeWeatherReportsIfNotAllCitiesAreValid() {
        List<String> citiesList = List.of("Tallinn", "Tartu", "Asdasdasd", "Privet", "Gorod", "Narva");
        Mockito.when(weatherFileMock.getCitiesFromFile(anyString())).thenReturn(citiesList);

        List<WeatherReport> reports = weatherWise.getWeatherReportFromFile(anyString());

        assertEquals(3, reports.size());
    }

    @Test
    public void shouldNotWriteFilesIfNoCities() {
        List<String> citiesList = new ArrayList<>();
        Mockito.when(weatherFileMock.getCitiesFromFile(anyString())).thenReturn(citiesList);

        weatherWise.getWeatherReportFromFile(anyString());

        verify(weatherFileMock, times(0)).writeReportsToFile(any());
    }

    @Test
    public void shouldInvokeWriteMethodFourTimesIfFourCities() {
        List<String> citiesList = List.of("Tartu", "Narva", "Ivangorod", "Viljandi");
        Mockito.when(weatherFileMock.getCitiesFromFile(anyString())).thenReturn(citiesList);

        weatherWise.getWeatherReportFromFile(anyString());

        verify(weatherFileMock, times(4)).writeReportsToFile(any());
    }

    @Test
    public void shouldInvokeWriteMethodTimesThereAreValidCities() {
        List<String> citiesList = List.of("Tallinn", "Not valid", "Maybe valid? No", "Helsinki", "  Mkm  ");
        Mockito.when(weatherFileMock.getCitiesFromFile(anyString())).thenReturn(citiesList);

        weatherWise.getWeatherReportFromFile(anyString());

        verify(weatherFileMock, times(2)).writeReportsToFile(any());
    }
}