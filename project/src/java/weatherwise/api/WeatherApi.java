package weatherwise.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import weatherwise.api.response.CurrentWeatherData;
import weatherwise.api.response.ForecastData;

import static com.sun.jersey.api.client.Client.create;
import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;
import static java.lang.Boolean.TRUE;

public class WeatherApi {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
    private static final String APPID = "5f903afd7add59c6b202889c825fda8f";
    private static Client client = getConfiguredClient();

    private static Client getConfiguredClient() {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JacksonJaxbJsonProvider.class);
        config.getFeatures().put(FEATURE_POJO_MAPPING, TRUE);
        return create(config);
    }

    public CurrentWeatherData getCurrentWeatherDataForCity(String cityName) {
        return getCurrentWeatherResponse(cityName).getEntity(CurrentWeatherData.class);
    }

    public ForecastData getForecastDataForCity(String cityName) {
        return getForecastResponse(cityName).getEntity(ForecastData.class);
    }

    public ClientResponse getCurrentWeatherResponse(String cityName) {
        String resourceUrl = BASE_URL + "/weather";
        return client.resource(resourceUrl)
                .queryParam("q", cityName)
                .queryParam("APPID", APPID)
                .queryParam("units", "metric")
                .get(ClientResponse.class);
    }

    public ClientResponse getForecastResponse(String cityName) {
        String resourceUrl = BASE_URL + "/forecast";
        return client.resource(resourceUrl)
                .queryParam("q", cityName)
                .queryParam("APPID", APPID)
                .queryParam("units", "metric")
                .get(ClientResponse.class);
    }
}
