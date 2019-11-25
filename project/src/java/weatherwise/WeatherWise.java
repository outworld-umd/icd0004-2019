package weatherwise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weatherwise.api.WeatherApi;
import weatherwise.api.dto.ListDto;
import weatherwise.api.dto.MainDto;
import weatherwise.api.response.CurrentWeatherData;
import weatherwise.api.response.ForecastData;
import weatherwise.exception.CityIsEmptyException;
import weatherwise.exception.CityNotFoundException;
import weatherwise.exception.CurrentWeatherDataMissingException;
import weatherwise.exception.ForecastWeatherDataMissingException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class WeatherWise {

    private Logger logger = LoggerFactory.getLogger(WeatherWise.class);

    private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
    private WeatherApi weatherApi;
    private WeatherFile weatherFile;

    public static void main(String[] args) {
        WeatherWise weatherWise = new WeatherWise(new WeatherApi());
        System.out.println(weatherWise.getWeatherReportForCity("Tallinn"));
    }

    public WeatherWise(WeatherApi weatherApi) {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.weatherApi = weatherApi;
        this.weatherFile = new WeatherFile();
    }

    public WeatherWise(WeatherApi weatherApi, WeatherFile weatherFile) {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.weatherApi = weatherApi;
        this.weatherFile = weatherFile;
    }

    public List<WeatherReport> getWeatherReportFromFile(String path) {
        List<String> cityList = weatherFile.getCitiesFromFile(path);
        List<WeatherReport> weatherReports = cityList.stream().map(c -> {
            try {
                return getWeatherReportForCity(c);
            } catch (RuntimeException e) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        weatherReports.forEach(r -> weatherFile.writeReportsToFile(r));
        return weatherReports;
    }

    public WeatherReport getWeatherReportForCity(String city) {
        WeatherReport weatherReport = new WeatherReport();
        if (isCityMissing(city)) {
            logger.error("Error CityIsEmptyException occurred when tried to get info for city '{}'", city);
            throw new CityIsEmptyException("City is empty");
        }
        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);
        if (isCityNotFound(currentWeatherData)) {
            logger.error("Error CityNotFoundException occurred when tried to get info for city '{}'", city);
            throw new CityNotFoundException("City not found");
        }
        try {
            weatherReport.setWeatherReportDetails(getWeatherReportDetails(currentWeatherData));
            weatherReport.setCurrentWeatherReport(getCurrentWeatherReport(currentWeatherData));
        } catch (NullPointerException e) {
            logger.error("NullPointerException occurred while processing '{}' for city '{}'", currentWeatherData, city);
            throw new CurrentWeatherDataMissingException("Current weather data missing");
        }
        ForecastData forecastData = weatherApi.getForecastDataForCity(city);
        try {
            weatherReport.setForecastReport(getForecastReports(forecastData));
            logger.info("Successfully got WeatherReport for city '{}'", city);
            return weatherReport;
        } catch (NullPointerException e) {
            logger.error("NullPointerException occurred while processing '{}' for city '{}'", forecastData, city);
            throw new ForecastWeatherDataMissingException("Forecast weather data missing");
        }
    }

    public WeatherReportDetails getWeatherReportDetails(CurrentWeatherData currentWeatherData) {
        WeatherReportDetails weatherReportDetails = new WeatherReportDetails();
        weatherReportDetails.setCity(currentWeatherData.getName());
        String lat = currentWeatherData.getCoord().getLat().toString();
        String lon = currentWeatherData.getCoord().getLon().toString();
        weatherReportDetails.setCoordinates(lat + "," + lon);
        return weatherReportDetails;
    }

    private CurrentWeatherReport getCurrentWeatherReport(CurrentWeatherData currentWeatherData) {
        CurrentWeatherReport currentWeatherReport = new CurrentWeatherReport();
        currentWeatherReport.setDate(sdf.format(new Date((currentWeatherData.getDt() + currentWeatherData.getTimezone()) * 1000)));
        currentWeatherReport.setTemperature(currentWeatherData.getMain().getTemp());
        currentWeatherReport.setHumidity(currentWeatherData.getMain().getHumidity());
        currentWeatherReport.setPressure(currentWeatherData.getMain().getPressure());
        return currentWeatherReport;
    }

    private ArrayList<ForecastReport> getForecastReports(ForecastData fData) {
        ArrayList<ForecastReport> forecastReport = new ArrayList<>();
        LinkedHashMap<String, ArrayList<MainDto>> map = getWeatherMap(fData);
        map.forEach((k, v) -> {
            ForecastReport report = new ForecastReport();
            report.setDate(k);
            report.setWeather(getAverageWeatherForDay(v));
            forecastReport.add(report);
        });
        return forecastReport;
    }

    private LinkedHashMap<String, ArrayList<MainDto>> getWeatherMap(ForecastData forecastData) {
        LinkedHashMap<String, ArrayList<MainDto>> map = new LinkedHashMap<>();
        String today = sdf.format(new Date(System.currentTimeMillis() + forecastData.getTimezone() * 1000));
        for (ListDto dto : forecastData.getList()) {
            String date = sdf.format(new Date((dto.getDt() + forecastData.getTimezone()) * 1000));
            if (!map.containsKey(date) && map.size() == 3) break;
            if (!date.equals(today)) {
                if (!map.containsKey(date)) map.put(date, new ArrayList<>());
                map.get(date).add(dto.getMain());
            }
        }
        return map;
    }

    public ForecastWeatherReport getAverageWeatherForDay(ArrayList<MainDto> weatherList) {
        ForecastWeatherReport weather = new ForecastWeatherReport();
        weather.setTemperature(Math.round(weatherList.stream().mapToDouble(MainDto::getTemp).average().orElseThrow()));
        weather.setHumidity(Math.round(weatherList.stream().mapToDouble(MainDto::getHumidity).average().orElseThrow()));
        weather.setPressure(Math.round(weatherList.stream().mapToDouble(MainDto::getPressure).average().orElseThrow()));
        return weather;
    }

    private boolean isCityMissing(String city) {
        return city == null || city.isEmpty();
    }

    private boolean isCityNotFound(CurrentWeatherData currentWeatherData) {
        return currentWeatherData.getMessage() != null;
    }
}
