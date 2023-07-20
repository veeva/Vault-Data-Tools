package com.veeva.vault.tools.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import org.apache.log4j.Logger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);

    /**
     * Determines if user-provided input file exists
     *
     * @param inputFile - File provided by user input
     * @return - True if provided file exists, otherwise false
     */
    public static boolean getInputFile(File inputFile) {
        if (inputFile == null) {
            logger.error("Unexpected error loading input file.");
            return false;
        } else if (!inputFile.exists()) {
            logger.error("File does not exist [" + inputFile.getAbsolutePath() + "]");
            return false;
        }
        return true;
    }

    /**
     * Reads the input file CSV and loads the data into a HashMap
     *
     * @param inputFile - File provided by user input
     * @return - HashMap containing the contents of the input file
     */
    public static HashMap<String, List<String>> getInputFileData(File inputFile) {
        // Reads the CSV and returns the contents
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(inputFile)).withSkipLines(1).build()) {

            HashMap<String, List<String>> inputData = new HashMap<>();
            String[] nextRow;

            while ((nextRow = csvReader.readNext()) != null) {
                if (nextRow.length != 0) {

                    // If there are multiple rows for this object, append the idParamValue (in column 3) to current list
                    if (inputData.containsKey(nextRow[0]) && nextRow.length >= 3) {
                        inputData.get(nextRow[0]).add(nextRow[2]);
                    } else if (nextRow.length >= 3) {
                        // First column is the object name, which is our key
                        // Second and third column are the idParam and idParamValue
                        inputData.put(nextRow[0], new ArrayList<>(Arrays.asList(nextRow).subList(1, 3)));
                    } else { // Case where input file does not have idParam/idParamValue columns
                        inputData.put(nextRow[0], new ArrayList<>());
                    }
                }
            }

            if (inputData.isEmpty()) {
                logger.error("Provided input file is empty.");
                return null;
            }

            return inputData;

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * Initializes a CSVWriter
     *
     * @param fileName - output file the CSVWriter will write to
     * @return - initialized CSVWriter, or null if failed to initialize
     */
    public static CSVWriter getCsvWriter(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            return new CSVWriter(writer);
        } catch (IOException e) {
            logger.error("Unexpected error creating CSVWriter: " + e.getMessage());
            return null;
        }
    }

    /**
     * Writes output data to CSV
     *
     * @param outputData - data to write to CSV
     * @param csvWriter  - CSVWriter used to write output
     */
    public static void writeDataToCsv(List<String[]> outputData, CSVWriter csvWriter) {
        try {
            for (String[] row : outputData) {
                csvWriter.writeNext(row);
            }
            csvWriter.flush();
        } catch (IOException e) {
            logger.error("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Flush and close the provided CSVWriter
     *
     * @param csvWriter - CSVWriter to flush and close
     */
    public static void closeCsvWriter(CSVWriter csvWriter) {
        try {
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            logger.error("Unexpected error closing CSVWriter: " + e.getMessage());
        }
    }

    /**
     * Appends current DateTime to provided file name
     *
     * @param fileName - output file name
     * @return - formatted output file name
     */
    public static String formatFileName(String fileName) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return formatter.format(LocalDateTime.now()) + "-" + fileName;
    }
}
