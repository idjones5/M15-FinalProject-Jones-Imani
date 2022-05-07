package com.company.Final.Project.fileWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.company.Final.Project.caching.CachedWeather;

import static com.company.Final.Project.FinalProjectApplication.getDataForFile;

public class WeatherFile {

    private FileWriter csWriter;

    // method to create a new file named weatherData.csv
    // this will store all the weather data from the locations the user requested
    // this file will append the weather data to an already existing file or a new file

    public void dataToCsv(String loc, String main, String desc, String temp, String feelslike, String fileName) {

        List<List<String>> rows = Collections.singletonList(
                Arrays.asList(loc, main, desc, temp, feelslike)
        );

        String file = fileName + ".csv";

        try {
            csWriter = new FileWriter(file, true);

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

    public static void newCSVFile(String userInput, Scanner scan, WeatherFile data,
                                  HashMap<Integer, String> filenames, HashMap<String, CachedWeather> map) {

        try {
            if (filenames.isEmpty()) {
                System.out.println("What would you like to name your file?");
                String filename = scan.nextLine();

                getDataForFile(map.get(userInput), data, userInput, filename);
                filenames.put(1, filename);

                System.out.println("\n" + "Success.");
            } else {
                String lastAddedFile = filenames.get(1);

                System.out.println("Do you want to store this data in "
                        + lastAddedFile + "?");
                String choice = scan.nextLine();

                if (choice.equals("y")) {

                    getDataForFile(map.get(userInput), data, userInput,
                            filenames.get(1));

                    System.out.println("\n" + "Success.");
                } else if (choice.equals("n")) {

                    System.out.println("What would you like to name your file?");
                    String newFileName = scan.nextLine();

                    getDataForFile(map.get(userInput), data, userInput, newFileName);
                    filenames.put(1, newFileName);

                    System.out.println("\n" + "Success.");
                } else {
                    System.out.println("Error.");
                }
            }

        } catch (Exception e) {
            System.out.println("Sorry,Data could not be added at this time." +
                    " Please try again.");
        }
    }


    public FileWriter getCsWriter() {
        return csWriter;
    }
}
