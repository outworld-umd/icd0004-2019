package weatherwise;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weatherwise.exception.CitiesFileNotFoundException;
import weatherwise.exception.FileIsEmptyException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherFile {

    private Logger logger = LoggerFactory.getLogger(WeatherFile.class);
    private Gson g = new Gson();

    public List<String> getCitiesFromFile(String path) {
        ArrayList<String> cityList = new ArrayList<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) if (!line.isEmpty()) cityList.add(line);
        } catch (IOException e) {
            logger.error("Error {} occurred while reading file {}", e, path);
            throw new CitiesFileNotFoundException("File not found");
        }
        if (cityList.isEmpty()) {
            logger.error("Input file {} was empty", path);
            throw new FileIsEmptyException("File is empty");
        }
        logger.info("Retrieved {} cities from file {}", cityList.size(), path);
        return cityList;
    }

    public void writeReportsToFile(WeatherReport weatherReport) {
        try (Writer writer = new FileWriter(weatherReport.getWeatherReportDetails().getCity() + ".json")) {
            writer.append(g.toJson(weatherReport));
            writer.flush();
            logger.info("Writing file was successful. File {}.json created", weatherReport.getWeatherReportDetails().getCity());
        } catch (IOException e) {
            logger.error("Error {} occurred while writing file for city '{}'", e, weatherReport.getWeatherReportDetails().getCity());
        }

    }
}
