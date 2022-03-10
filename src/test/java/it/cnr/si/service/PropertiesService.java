package it.cnr.si.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {

    private static Properties prop = null;

    public static Properties getInstance() {
        InputStream resourceStream = PropertiesService.class.getClassLoader().getResourceAsStream("application.properties");
        if (prop == null) {
            prop = new Properties();
            try {
                prop.load(resourceStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }

    public static String getProp(String key) {
        String keyEnv = key.toUpperCase().replace('.','_');
        return System.getenv(keyEnv) == null ?
                getInstance().getProperty(key) :
                System.getenv(keyEnv);
    }

}
