package weatherwise.api.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainDto {
    private Double temp;
    private Double temp_min;
    private Double temp_max;
    private Integer pressure;
    private Double sea_level;
    private Double grnd_level;
    private Integer humidity;
    private Double temp_kf;
}
