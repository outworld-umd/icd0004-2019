package weatherwise.api;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import weatherwise.api.response.CurrentWeatherData;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import weatherwise.api.response.ForecastData;

import static com.sun.jersey.api.client.Client.create;
import static com.sun.jersey.api.json.JSONConfiguration.FEATURE_POJO_MAPPING;
import static java.lang.Boolean.TRUE;

public class WeatherApi {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
    private static final String APPID = "5f903afd7add59c6b202889c825fda8f";
    private static Client client = getConfiguredClient();

    public CurrentWeatherData getCurrentWeatherDataForCity(String cityName) {
        return getClientResponse(cityName, "weather").getEntity(CurrentWeatherData.class);
    }

    public ForecastData getForecastDataForCity(String cityName) {
        ClientResponse r = getClientResponse(cityName, "forecast");
        System.out.println(r);
        return r.getEntity(ForecastData.class);
    }

    private ClientResponse getClientResponse(String cityName, String type) {
        String resourceUrl = BASE_URL + "/" + type;
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
