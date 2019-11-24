package weatherwise;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import weatherwise.api.WeatherApi;
import weatherwise.api.dto.ListDto;
import weatherwise.api.dto.MainDto;
import weatherwise.api.response.CurrentWeatherData;
import weatherwise.api.response.ForecastData;
import weatherwise.exception.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WeatherWise {

    private WeatherApi weatherApi;
    private static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");

    public WeatherWise(WeatherApi weatherApi) {
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        this.weatherApi = weatherApi;
    }

    public void getWeatherReportFromFile(String path) throws IOException {
        ArrayList<String> cityList = getCitiesFromFile(path);
        Gson g = new GsonBuilder().create();
        for (String city : cityList) {
            try {
                WeatherReport weatherReport = getWeatherReportForCity(city);
                Writer writer = new FileWriter("src/outputs/" + city + ".json");
                g.toJson(weatherReport, writer);
                writer.flush();
                writer.close();
            } catch (CityNotFoundException ignored) {}
        }
    }

    private ArrayList<String> getCitiesFromFile(String path) throws IOException {
        ArrayList<String> cityList = new ArrayList<>();
        String line;
        BufferedReader br = new BufferedReader(new FileReader(path));
        while ((line = br.readLine()) != null) if (!line.isEmpty()) cityList.add(line);
        if (cityList.isEmpty()) throw new FileIsEmptyException("File is empty");
        return cityList;
    }

    public WeatherReport getWeatherReportForCity(String city) {
        WeatherReport weatherReport = new WeatherReport();
        if (isCityMissing(city)) throw new CityIsEmptyException("City is empty");
        CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);
        ForecastData forecastData = weatherApi.getForecastDataForCity(city);
        try {
            if (isCityNotFound(currentWeatherData)) throw new CityNotFoundException("City not found");
            weatherReport.setWeatherReportDetails(getWeatherReportDetails(currentWeatherData));
            weatherReport.setCurrentWeatherReport(getCurrentWeatherReport(currentWeatherData));
        } catch (NullPointerException e) {
            throw new CurrentWeatherDataMissingException("Current weather data missing");
        }
        try {
            weatherReport.setForecastReport(getForecastReports(forecastData));
            return weatherReport;
        } catch (NullPointerException e) {
            throw new ForecastWeatherDataMissingException("Forecast weather data missing");
        }
    }

    private WeatherReportDetails getWeatherReportDetails(CurrentWeatherData currentWeatherData) {
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

    private ForecastWeatherReport getAverageWeatherForDay(ArrayList<MainDto> weatherList) {
        ForecastWeatherReport weather = new ForecastWeatherReport();
        weather.setTemperature(weatherList.stream().mapToDouble(MainDto::getTemp).average().orElse(0));
        weather.setHumidity((int) weatherList.stream().mapToDouble(MainDto::getHumidity).average().orElse(0));
        weather.setPressure((int) weatherList.stream().mapToDouble(MainDto::getPressure).average().orElse(0));
        return weather;
    }

    private boolean isCityMissing(String city) {
        return city == null || city.isEmpty();
    }

    private boolean isCityNotFound(CurrentWeatherData currentWeatherData) {
        return currentWeatherData.getMessage() != null;
    }
}
