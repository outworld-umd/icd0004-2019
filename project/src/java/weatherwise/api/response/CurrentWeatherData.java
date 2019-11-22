package weatherwise.api.response;

import weatherwise.api.dto.CoordinatesDto;
import weatherwise.api.dto.MainDto;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherData {
    private CoordinatesDto coord;
    private MainDto main;
    private String name;
    private String id;
    private Integer cod;
    private String base;
    private String message;
}
