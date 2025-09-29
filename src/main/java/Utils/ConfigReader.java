package Utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    public Properties prop;

    /**
     * This method is used to load the properties from config.properties file
     *
     * @return it returns Properties prop object
     */
    public Properties initProp() {

        prop = new Properties();
        try {
            String path = "./src/main/resources/details.properties";
            FileInputStream ip = new FileInputStream(path);
            prop.load(ip);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error occurred while loading properties file");
            System.out.println(e.getMessage());
        }
        return prop;
    }

}
