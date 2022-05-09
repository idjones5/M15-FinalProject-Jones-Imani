package com.company.Final.Project.fileWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneralDataToFileMethods {

    private FileWriter csWriter;

    // method to create a new csv file
    // this method will convert all the rows in addToCSV as the data
    // and names in the headers list will be used to name the columns in the csv file

    public void dataToCsv(List<String> addToCsv, List<String> headers, String fileName) {

        List<List<String>> rows = new ArrayList<>();
        rows.add(addToCsv);

        String file = fileName + ".csv";

        try {
            csWriter = new FileWriter(file, true);

            for (int i=0; i < headers.size(); i++) {
                if (i == headers.size() - 1) {
                    csWriter.append(headers.get(i));
                    csWriter.append("\n");
                } else {
                    csWriter.append(headers.get(i));
                    csWriter.append(",");
                }
            }

            for (List<String> rowData : rows) {
                csWriter.append(String.join(",", rowData));
                csWriter.append("\n");
            }


        } catch (IOException e) {
            System.out.println("An error has occurred while writing the Csv file.");
        }

    }


    public FileWriter getCsWriter() {
        return csWriter;
    }
}
