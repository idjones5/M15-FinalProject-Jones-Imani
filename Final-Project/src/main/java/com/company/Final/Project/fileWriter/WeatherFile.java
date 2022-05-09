package com.company.Final.Project.fileWriter;

import java.io.IOException;
import java.util.*;
import com.company.Final.Project.caching.CachedWeather;


public class WeatherFile {

    // method that gets the data from UserI and converts preps it to store in file
    // and the headers specific to this class created for file

    public static void getDataForFile(CachedWeather userW, String fileName) {

        List<String> list = Arrays.asList(
                userW.getLocation(),
                userW.getMain(),
                userW.getDescription(),
                userW.getTemperature(),
                userW.getFeelsLike()
        );

        List<String> headers = Arrays.asList("Location", "Main Weather", "Detail", "Temperature", "FeelsLike");
        GeneralDataToFileMethods gm = new GeneralDataToFileMethods();

        gm.dataToCsv(list, headers, fileName);

        try {
            gm.getCsWriter().flush();
            gm.getCsWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // method that allows user to create a csv file and name that file

        public static void newCSVFile(String userInput, Scanner scan,
                                      HashMap<Integer, String> filenames, HashMap<String, CachedWeather> map) {

        try {
            if (filenames.isEmpty()) {
                System.out.println("What would you like to name your file?");
                String filename = scan.nextLine();

                getDataForFile(map.get(userInput), filename);
                filenames.put(1, filename);

                System.out.println("\n" + "Success.");
            } else {
                String lastAddedFile = filenames.get(1);

                System.out.println("Do you want to store this data in " + lastAddedFile + "?");
                String choice = scan.nextLine();

                if (choice.equals("y")) {
                    getDataForFile(map.get(userInput), filenames.get(1));

                    System.out.println("\n" + "Success.");
                } else if (choice.equals("n")) {
                    System.out.println("What would you like to name your file?");
                    String newFileName = scan.nextLine();

                    getDataForFile(map.get(userInput), newFileName);
                    filenames.put(1, newFileName);

                    System.out.println("\n" + "Success.");
                } else {
                    System.out.println("Error.");
                }
            }

        } catch (Exception e) {
            System.out.println("Sorry, Data could not be added at this time." +
                    " Please try again.");
        }
    }

}
