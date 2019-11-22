package weatherwise;

import lombok.Data;

@Data
public class ForecastReport {
    private String date;
    private ForecastWeatherReport weather;
}
