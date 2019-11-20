package weatherwise;

import weatherwise.api.WeatherApi;
import weatherwise.api.response.CurrentWeatherData;
import weatherwise.exception.CityIsEmptyException;
import weatherwise.exception.CurrentWeatherDataMissingException;

public class WeatherWise {

    private WeatherApi weatherApi;

    public WeatherWise(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public WeatherReport getWeatherReportForCity(String city) throws CityIsEmptyException, CurrentWeatherDataMissingException {
        WeatherReport weatherReport = new WeatherReport();
        WeatherReportDetails weatherReportDetails = new WeatherReportDetails();

        if (isCityMissing(city)) {
            throw new CityIsEmptyException("City is empty");
        } else {
            CurrentWeatherData currentWeatherData = weatherApi.getCurrentWeatherDataForCity(city);
            if (currentWeatherData != null) {
                weatherReportDetails.setCity(currentWeatherData.getName());
                weatherReport.setWeatherReportDetails(weatherReportDetails);
                return weatherReport;
            } else {
                throw new CurrentWeatherDataMissingException("Current weather data missing");
            }
        }
    }

    private boolean isCityMissing(String city) {
        return city == null || city.isEmpty();
    }
}
