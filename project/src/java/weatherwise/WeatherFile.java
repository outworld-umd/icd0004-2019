package weatherwise;

import com.google.gson.Gson;
import weatherwise.exception.CitiesFileNotFoundException;
import weatherwise.exception.FileIsEmptyException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherFile {

    private static Gson g = new Gson();

    public List<String> getCitiesFromFile(String path) {
        ArrayList<String> cityList = new ArrayList<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) if (!line.isEmpty()) cityList.add(line);
        } catch (IOException e) {
            // logging goes here
            throw new CitiesFileNotFoundException("File not found");
        }
        if (cityList.isEmpty()) throw new FileIsEmptyException("File is empty");
        return cityList;
    }

    public void writeReportsToFile(WeatherReport weatherReport) {
        try (Writer writer = new FileWriter("src/outputs/" + weatherReport.getWeatherReportDetails().getCity() + ".json")) {
            writer.append(g.toJson(weatherReport));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
