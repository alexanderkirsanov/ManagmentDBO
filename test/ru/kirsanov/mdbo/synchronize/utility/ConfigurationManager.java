package ru.kirsanov.mdbo.synchronize.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationManager {

    private static final String configurationFileName = "config";
    private File configurationFile;

    public ConfigurationManager() {
        configurationFile = new File(configurationFileName);
    }

    public Properties loadProperties() {
        Properties properties = new Properties();
        if (configurationFile.exists()) {
            if (configurationFile.canRead()) {
                try {
                    FileInputStream configurationFileInputStream = new FileInputStream(
                            configurationFile);
                    try {
                        properties.load(configurationFileInputStream);
                    } catch (Exception e) {
                        //do nothing
                    } finally {
                        configurationFileInputStream.close();
                    }
                } catch (Exception e) {
                    //do nothing
                }
            }
        }
        return properties;
    }
}
