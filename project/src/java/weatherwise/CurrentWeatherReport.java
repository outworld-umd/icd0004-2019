package weatherwise;

import lombok.Data;

@Data
public class CurrentWeatherReport {
    private Double temperature;
    private Integer humidity;
    private Integer pressure;
}
