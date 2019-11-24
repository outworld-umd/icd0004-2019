package unittest;

import org.junit.Before;
import org.junit.Test;
import weatherwise.ForecastReport;
import weatherwise.WeatherReport;
import weatherwise.WeatherWise;
import weatherwise.api.WeatherApi;
import weatherwise.api.dto.CoordinatesDto;
import weatherwise.api.response.CurrentWeatherData;
import weatherwise.exception.CityIsEmptyException;
import weatherwise.exception.CityNotFoundException;
import weatherwise.exception.FileIsEmptyException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.*;


public class UnitTests {

    private static String city;
    private static WeatherApi weatherApi;
    private static WeatherWise weatherWise;

    private static Function<String, String> capitalizeFunc = s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();

    @Before
    public void setUp() {
        city = "Tallinn";
        weatherApi = new WeatherApi();
        weatherWise = new WeatherWise(weatherApi);
    }

    private String getDateString(CurrentWeatherData currentWeatherData, int dayDiffFromToday) {
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date todayDate = new Date(System.currentTimeMillis() + (currentWeatherData.getTimezone() + dayDiffFromToday * 86400) * 1000);
        return format.format(todayDate);
    }

    @Test(expected = CityIsEmptyException.class)
    public void shouldThrowCityIsEmptyExceptionIfCityIsNull() {
        weatherWise.getWeatherReportForCity(null);
    }

    @Test(expected = CityIsEmptyException.class)
    public void shouldThrowCityIsEmptyExceptionIfCityIsEmptyString() {
        weatherWise.getWeatherReportForCity("");
    }

    @Test(expected = CityNotFoundException.class)
    public void shouldThrowCityNotFoundExceptionIfNonexistentCity() {
        weatherWise.getWeatherReportForCity("Nonexistent City");
    }

    @Test
    public void weatherReportShouldBeMadeForCorrectCity() {
        String city = "Beijing";
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);

        assertEquals(city, weatherReport.getWeatherReportDetails().getCity());
    }

    @Test
    public void unitShouldBeCelsiusIfRequestMetric() {
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);

        assertEquals("Celsius", weatherReport.getWeatherReportDetails().getTemperatureUnit());
    }

    @Test
    public void coordinatesAndOrderShouldBeCorrectInWeatherReport() {
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);
        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);
        CoordinatesDto coordinates = currentWeatherData.getCoord();

        String expectedCoordinates = coordinates.getLat().toString() + "," + coordinates.getLon().toString();

        assertEquals(expectedCoordinates, weatherReport.getWeatherReportDetails().getCoordinates());
    }

    @Test
    public void shouldGetWeatherReportIfCityUpperCase() {
        String city = "TALLINN";
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);

        assertEquals(weatherReport.getWeatherReportDetails().getCity(), capitalizeFunc.apply(city));
    }

    @Test
    public void shouldGetWeatherReportIfCityIsMessedCase() {
        String city = "tAlLiNn";
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);

        assertEquals(weatherReport.getWeatherReportDetails().getCity(), capitalizeFunc.apply(city));
    }

    @Test
    public void temperatureInFinalReportMustBeSameAsInCurrentWeatherData() {
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);
        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);

        Double temperature = currentWeatherData.getMain().getTemp();

        assertEquals(temperature, weatherReport.getCurrentWeatherReport().getTemperature());
    }

    @Test
    public void forecastReportShouldContainThreeDays() {
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);

        assertEquals(3, weatherReport.getForecastReport().size());
    }

    @Test
    public void currentWeatherReportShouldBeForTodayAndCorrectFormat() {
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);
        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);

        String today = getDateString(currentWeatherData,0);

        assertEquals(today, weatherReport.getCurrentWeatherReport().getDate());
    }

    @Test
    public void forecastDataFirstDateShouldBeTomorrow() {
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);
        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);

        String tomorrow = getDateString(currentWeatherData, 1);

        assertEquals(tomorrow, weatherReport.getForecastReport().get(0).getDate());
    }

    @Test
    public void forecastDataShouldBeForThreeConsecutiveDays() {
        WeatherReport weatherReport = weatherWise.getWeatherReportForCity(city);
        CurrentWeatherData cwd = weatherApi.getCurrentWeatherDataForCity(city);

        String[] expectedDates = new String[] {getDateString(cwd, 1), getDateString(cwd, 2), getDateString(cwd, 3)};
        String[] actualDates = weatherReport.getForecastReport().stream().map(ForecastReport::getDate).toArray(String[]::new);

        assertArrayEquals(expectedDates, actualDates);
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowFileNotFoundExceptionIfNoFile() throws IOException {
        File file = new File("gibberish.txt");
        weatherWise.getWeatherReportFromFile(file.getAbsolutePath());
    }

    @Test(expected = FileIsEmptyException.class)
    public void shouldThrowFileIsEmptyExceptionIfFileIsEmpty() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("empty_input.txt")).getFile());
        weatherWise.getWeatherReportFromFile(file.getAbsolutePath());
    }

    @Test
    public void shouldCreateOneOutputFileIfOneCityInFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("one_city.txt")).getFile());
        weatherWise.getWeatherReportFromFile(file.getAbsolutePath());
    }

    @Test
    public void shouldCreateMultipleOutputFilesIfMultipleCitiesInFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("multiple_cities.txt")).getFile());
        weatherWise.getWeatherReportFromFile(file.getAbsolutePath());
    }

    @Test
    public void shouldCreateSomeFilesIfSomeCitiesAreIncorrect() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("right_wrong_cities.txt")).getFile());
        weatherWise.getWeatherReportFromFile(file.getAbsolutePath());
    }

}
