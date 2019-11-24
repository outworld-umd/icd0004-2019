package unittest;

import org.junit.Before;
import org.junit.Test;
import weatherwise.*;
import weatherwise.api.WeatherApi;
import weatherwise.api.dto.CoordinatesDto;
import weatherwise.api.dto.MainDto;
import weatherwise.api.response.CurrentWeatherData;
import weatherwise.exception.CitiesFileNotFoundException;
import weatherwise.exception.CityIsEmptyException;
import weatherwise.exception.CityNotFoundException;
import weatherwise.exception.FileIsEmptyException;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class UnitTests {

    private static String city;
    private static WeatherApi weatherApi;
    private static WeatherWise weatherWise;
    private static WeatherFile weatherFile;

    private static Function<String, String> capitalizeFunc = s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    private static final double DELTA = 0.0001;

    @Before
    public void setUp() {
        city = "Tallinn";
        weatherApi = new WeatherApi();
        weatherWise = new WeatherWise(weatherApi);
        weatherFile = new WeatherFile();
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
        CurrentWeatherData currentWeatherData = new CurrentWeatherData();
        CoordinatesDto coords = new CoordinatesDto();
        coords.setLon(123.23);
        coords.setLat(-421.0);
        currentWeatherData.setCoord(coords);

        WeatherReportDetails weatherReportDetails = weatherWise.getWeatherReportDetails(currentWeatherData);

        assertEquals("-421.0,123.23", weatherReportDetails.getCoordinates());
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

        String today = getDateString(currentWeatherData, 0);

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

        String[] expectedDates = new String[]{getDateString(cwd, 1), getDateString(cwd, 2), getDateString(cwd, 3)};
        String[] actualDates = weatherReport.getForecastReport().stream().map(ForecastReport::getDate).toArray(String[]::new);

        assertArrayEquals(expectedDates, actualDates);
    }

    @Test(expected = CitiesFileNotFoundException.class)
    public void shouldThrowFileNotFoundExceptionIfNoFile() {
        File file = new File("gibberish.txt");
        weatherWise.getWeatherReportFromFile(file.getAbsolutePath());
    }

    @Test(expected = FileIsEmptyException.class)
    public void shouldThrowFileIsEmptyExceptionIfFileIsEmpty() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("empty_input.txt")).getFile());
        weatherWise.getWeatherReportFromFile(file.getAbsolutePath());
    }

    @Test
    public void shouldGetOneCityIfOneCityInFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("one_city.txt")).getFile());
        List<String> cities = weatherFile.getCitiesFromFile(file.getAbsolutePath());

        assertEquals(1, cities.size());
    }

    @Test
    public void shouldGetMultipleCitiesIfMultipleCitiesInFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("multiple_cities.txt")).getFile());
        List<String> cities = weatherFile.getCitiesFromFile(file.getAbsolutePath());

        assertEquals(3, cities.size());
    }

    @Test
    public void shouldGetSomeCitiesIfSomeLinesAreEmpty() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("right_wrong_cities.txt")).getFile());
        List<String> cities = weatherFile.getCitiesFromFile(file.getAbsolutePath());

        assertEquals(6, cities.size());
    }

    @Test
    public void forecastTemperatureShouldBeAverage() {
        ArrayList<MainDto> weatherData = getForecastDataForTest();

        ForecastWeatherReport forecastReports = weatherWise.getAverageWeatherForDay(weatherData);

        assertEquals(-10D, forecastReports.getTemperature(), DELTA);
    }

    @Test
    public void forecastHumidityShouldBeAverage() {
        ArrayList<MainDto> weatherData = getForecastDataForTest();

        ForecastWeatherReport forecastReports = weatherWise.getAverageWeatherForDay(weatherData);

        assertEquals(500, (int) forecastReports.getHumidity());
    }

    @Test
    public void forecastPressureShouldBeAverage() {
        ArrayList<MainDto> weatherData = getForecastDataForTest();

        ForecastWeatherReport forecastReports = weatherWise.getAverageWeatherForDay(weatherData);

        assertEquals(1025, (int) forecastReports.getPressure());
    }

    private String getDateString(CurrentWeatherData currentWeatherData, int dayDiffFromToday) {
        DateFormat format = new SimpleDateFormat("YYYY-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date todayDate = new Date(System.currentTimeMillis() + (currentWeatherData.getTimezone() + dayDiffFromToday * 86400) * 1000);
        return format.format(todayDate);
    }

    private ArrayList<MainDto> getForecastDataForTest() {
        MainDto main1 = new MainDto();
        MainDto main2 = new MainDto();
        MainDto main3 = new MainDto();
        main1.setTemp(-20.0);
        main2.setTemp(-10.0);
        main3.setTemp(0.0);
        main1.setHumidity(400);
        main2.setHumidity(500);
        main3.setHumidity(600);
        main1.setPressure(1000);
        main2.setPressure(1025);
        main3.setPressure(1050);
        return new ArrayList<>(Arrays.asList(main1, main2, main3));
    }

}
