package weatherwise.api.response;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import weatherwise.api.dto.CoordinatesDto;
import weatherwise.api.dto.MainDto;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherData {
    private CoordinatesDto coord;
    private MainDto main;
    private String name;
    private String id;
    private Integer cod;
    private String message;
    private Long dt;
    private Long timezone;
}
