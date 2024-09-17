package com.solvd.laba.jjaccomando;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public final class CustomFileIO {

    public static Logger logger = LogManager.getLogger();

    public static boolean findUniqueWordsInFile() {

        boolean successful = true;
        Scanner input = new Scanner(System.in);

        System.out.print("Please enter the file path: ");
        logger.warn("Receiving user input for file path.");


        /* Creates a file object using the path given by the user input.
        This input removes any enclosing quotations if the file path has them. */
        File myFile = null;
        String[] splitContent = null;

        try {
            if (input.hasNextLine()) {
                String strippedPath = StringUtils.strip(input.nextLine(), "\"");
                if (strippedPath != null) {
                    myFile = new File(strippedPath);
                    if (myFile.exists() && myFile.canRead()) {
                        logger.warn("Reading content from file.");
                        splitContent = StringUtils.split(FileUtils.readFileToString(myFile, "UTF-8").toLowerCase(), " \n\t\r.,;:!?()[]{}\"");
                    } else {
                        logger.error("File does not exist or cannot be read. Error: {IOException}");
                        successful = false;
                    }
                } else {
                    logger.error("The input line is null after stripping. Error: {NullPointerException}");
                    successful = false;
                }
            } else {
                logger.error("No input available. Error: {NoSuchElementException}");
                successful = false;
            }
        } catch (SecurityException securityException) {
            logger.error("Restricted access to file. Error: {SecurityException}");
            successful = false;
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error("Encoding \"UTF-8\" is not supported. Error: {IllegalArgumentException}");
            successful = false;
        } catch (IOException ioException) {
            logger.error("Unable to read from file. Error: {IOException}");
            successful = false;
        }

        if (!successful) {
            input.close();
            return false;
        }

        /* For each word in the splitContent array: add the word to the map as a key.
        The default value is set to 0 for if the word is not in the map.
        Everytime a word is added, the value that gets returned from that word as a key will increment by 1. */
        Map<String, Integer> wordCount = new HashMap<>();
        if(splitContent != null) {
            for (String word : splitContent) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        } else {
            input.close();
            return false;
        }

            /* For every word in the splitContent array, count the number of words that only appear once.
            If the value returned is 1, then the word only appeared once. */
        int uniqueWordCount = 0;
        for (String word : splitContent) {
            if (wordCount.getOrDefault(word, 0) == 1) {
                uniqueWordCount++;
            }
        }

        try {
            /* Write to the file by adding a new line with the number of unique words. */
            FileUtils.writeStringToFile(myFile, String.format("\nNumber of unique words: %d", uniqueWordCount), "UTF-8", true);
        } catch (IOException e) {
            logger.error("Unable to write to file. Error: {IOException}");
            successful = false;
        }

        input.close();
        return successful;
    }

}
