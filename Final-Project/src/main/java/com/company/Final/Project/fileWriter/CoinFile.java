package com.company.Final.Project.fileWriter;

import com.company.Final.Project.caching.CachedCoin;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CoinFile {

    // method that gets the data from UserI and converts preps it to store in file
    // and the headers specific to this class created for file

    public static void getDataForFile(CachedCoin userW, String fileName) {

        String noCommaPrice = userW.getPrice().replaceAll(",", "");

        List<String> list = Arrays.asList(
                userW.getSymbol(),
                userW.getName(),
                noCommaPrice
        );

        List<String> headers = Arrays.asList("Symbol", "Name", "Price (USD)");
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
                                  HashMap<Integer, String> filenames, HashMap<String, CachedCoin> map) {

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
