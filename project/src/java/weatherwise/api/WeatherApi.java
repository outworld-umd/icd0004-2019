package weatherwise.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import weatherwise.api.response.CurrentWeatherData;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import static com.sun.jersey.api.client.Client.create;
import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;
import static java.lang.Boolean.TRUE;

public class WeatherApi {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
    private static final String APPID = "[INSERT_API_KEY_HERE]";
    private static Client client = getConfiguredClient();

    public CurrentWeatherData getCurrentWeatherDataForCity(String cityName) {
        return getClientResponse(cityName).getEntity(CurrentWeatherData.class);
    }

    private ClientResponse getClientResponse(String cityName) {
        String resourceUrl = BASE_URL + "/weather";
        return client.resource(resourceUrl)
                .queryParam("q", cityName)
                .queryParam("APPID", APPID)
                .queryParam("units", "metric")
                .get(ClientResponse.class);
    }

    private static Client getConfiguredClient() {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(FEATURE_POJO_MAPPING, TRUE);
        return create(config);
    }
}
