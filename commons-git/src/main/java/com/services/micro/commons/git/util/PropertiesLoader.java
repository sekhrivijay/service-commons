package com.services.micro.commons.git.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);
    public static void load(Properties properties, String fileName) {
        InputStream input = null;
        try {
            LOGGER.info("filename is " + fileName);
            input = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(input);
        } catch (IOException ex) {
            LOGGER.error("Could not load properties from files " +  fileName, ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.error("Could not close input stream " +  fileName, e);
                }
            }
        }
    }
}
