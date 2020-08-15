package co.apt.config;

import co.apt.constant.OutputConstants;
import co.apt.model.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Config parser
 * Loads configuration from file to a public member config to be maintained as state of inventory
 * Uses jackson mapper to unmarshall json to config class
 */
public class Configuration {

    /**
     * Central state of configuration and inventory management to be accessed through out the application
     */
    public Config config;

    /**
     * Load config from file into member using jackson object mapper
     * @throws IOException
     */
    public void loadConfig() throws IOException {
        String configFile = getClass().getClassLoader().getResource(OutputConstants.configFile).getFile();
        File file = new File(configFile);
        ObjectMapper mapper = new ObjectMapper();
        config = mapper.readValue(file, Config.class);
    }

}
