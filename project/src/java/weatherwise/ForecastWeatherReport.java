package weatherwise;

import lombok.Data;

@Data
public class ForecastWeatherReport {
    private Long temperature;
    private Long humidity;
    private Long pressure;
}
