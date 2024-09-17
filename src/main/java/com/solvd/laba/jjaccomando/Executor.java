package com.solvd.laba.jjaccomando;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Executor {

    public static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("Searching for unique words in file: Program start...");
        if(CustomFileIO.findUniqueWordsInFile()) {
            logger.info("Program successful. Shutting down...");
        } else {
            logger.error("Program unsuccessful. Shutting down...");
        }
    }
}
