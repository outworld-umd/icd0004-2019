package weatherwise;

import lombok.Data;

import java.util.ArrayList;

@Data
public class WeatherReport {
    private WeatherReportDetails weatherReportDetails;
    private CurrentWeatherReport currentWeatherReport;
    private ArrayList<ForecastReport> forecastReport;
}
