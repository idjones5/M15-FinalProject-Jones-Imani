package com.company.Final.Project.fileWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ISSWeatherFile {

    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private String mainWeather;
    private String detail;
    private String temp;

    public static void getDataForFile(ISSWeatherFile userI, String fileName) {

        List<String> list = Arrays.asList(
                userI.getLatitude(),
                userI.getLongitude(),
                userI.getCountry(),
                userI.getCity(),
                userI.getMainWeather(),
                userI.getDetail(),
                userI.getTemp()
        );

        List<String> headers = Arrays.asList("Latitude", "Longitude", "Country", "City",
                "Main Weather", "Detail", "Temp");
        GeneralDataToFileMethods gm = new GeneralDataToFileMethods();

        gm.dataToCsv(list, headers, fileName);

        try {
            gm.getCsWriter().flush();
            gm.getCsWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void newCSVFile(Scanner scan, HashMap<Integer, String> filenames, HashMap<Integer, ISSWeatherFile> map) {

        try {
            if (filenames.isEmpty()) {
                System.out.println("What would you like to name your file?");
                String filename = scan.nextLine();

                getDataForFile(map.get(1), filename);
                filenames.put(1, filename);

                System.out.println("\n" + "Success.");
            } else {
                String lastAddedFile = filenames.get(1);

                System.out.println("Do you want to store this data in " + lastAddedFile + "?");
                String choice = scan.nextLine();

                if (choice.equals("y")) {

                    getDataForFile(map.get(1), filenames.get(1));

                    System.out.println("\n" + "Success.");
                } else if (choice.equals("n")) {

                    System.out.println("What would you like to name your file?");
                    String newFileName = scan.nextLine();

                    getDataForFile(map.get(1), newFileName);
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


    // getters and setters

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getMainWeather() {
        return mainWeather;
    }

    public String getDetail() {
        return detail;
    }

    public String getTemp() {
        return temp;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setMainWeather(String mainWeather) {
        this.mainWeather = mainWeather;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }
}
