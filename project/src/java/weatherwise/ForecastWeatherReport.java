package weatherwise;

import lombok.Data;

@Data
public class ForecastWeatherReport {
    private Double temperature;
    private Integer humidity;
    private Integer pressure;
}
