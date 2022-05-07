package com.company.Final.Project.fileWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.company.Final.Project.caching.CachedWeather;

public class WeatherFile {

    private FileWriter csWriter;

    // method to create a new file named weatherData.csv
    // this will store all the weather data from the locations the user requested
    // this file will continuously append the user data to the weatherData.csv file
    // it is recommended to copy the file and rename it if user wants to keep only a specific
    // set of data

    public void dataToCsv(String loc, String main, String desc, String temp, String feelslike) {

        List<List<String>> rows = Collections.singletonList(
                Arrays.asList(loc, main, desc, temp, feelslike)
        );

        try {
            csWriter = new FileWriter("weatherData.csv", true);

            csWriter.append("Location");
            csWriter.append(",");
            csWriter.append("Main Weather");
            csWriter.append(",");
            csWriter.append("Detail");
            csWriter.append(",");
            csWriter.append("Temperature");
            csWriter.append(",");
            csWriter.append("FeelsLike");
            csWriter.append("\n");

            for (List<String> rowData : rows) {
                csWriter.append(String.join(",", rowData));
                csWriter.append("\n");
            }


        } catch (IOException e) {
            System.out.println("An error has occurred while writing the Csv file.");
        }

        }

    public void getDataFromMap(CachedWeather userW, WeatherFile data) {

        data.dataToCsv(
                userW.getLocation(),
                userW.getMain(),
                userW.getDescription(),
                userW.getTemperature(),
                userW.getFeelsLike()
        );
        try {
            data.getCsWriter().flush();
            data.getCsWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileWriter getCsWriter() {
        return csWriter;
    }
}
