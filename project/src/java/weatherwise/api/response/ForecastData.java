package weatherwise.api.response;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import weatherwise.api.dto.ListDto;

import java.util.ArrayList;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastData {
    private ArrayList<ListDto> list;
    private Long timezone;

    @JsonProperty("city")
    private void unpackTimezoneFromCity(Map<String, Object> city) {
        timezone = Long.parseLong(city.get("timezone").toString());
    }
}
