package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class Reader {
	InputStream inputStream;
	Properties properties;
	private static Reader instance = null;
	
	private Reader(){}
	
	private static void createInstance() {
        if (instance == null) { 
        	instance = new Reader();
        }
    }

    public static Reader getInstance() {
        if (instance == null) {
        	createInstance();
        	try {
				instance.getConfigValues();
			} catch (IOException e) {
				e.printStackTrace();
			}
        };
        return instance;
    }
    
    public String getValue(String key){
    	return instance.properties.getProperty(key);
    }
	
	private void getConfigValues() throws IOException {
 
		try {
			properties = new Properties();
			String propFileName = "config.properties";
 
			inputStream = new FileInputStream(propFileName);
			properties.load(inputStream);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
	}
}