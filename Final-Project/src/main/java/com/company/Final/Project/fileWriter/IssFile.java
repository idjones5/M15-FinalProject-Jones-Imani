package com.company.Final.Project.fileWriter;

import java.io.IOException;
import java.util.*;

public class IssFile {

    private String latitude;
    private String longitude;
    private String country;
    private String city;


    // method that gets the data from UserI and converts preps it to store in file
    // and the headers specific to this class created for file

    public static void getDataForFile(IssFile userI, String fileName) {

        List<String> list = Arrays.asList(
                userI.getLatitude(),
                userI.getLongitude(),
                userI.getCountry(),
                userI.getCity()
        );

        List<String> headers = Arrays.asList("Latitude", "Longitude", "Country", "City");
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

    public static void newCSVFile(Scanner scan, HashMap<Integer, String> filenames, HashMap<Integer, IssFile> map) {

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


}
