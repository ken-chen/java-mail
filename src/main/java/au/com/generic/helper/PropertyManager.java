package  au.com.generic.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyManager {
	static Logger logger = Logger.getLogger(PropertyManager.class);
	
	private static Properties properties;

	private PropertyManager() {

	}

	public static String getProperty(String key) {
		String value = "";
		if (properties == null) {
			InputStream is = PropertyManager.class
					.getResourceAsStream("/environment.properties");
			properties = new Properties();
			try {
				properties.load(is);
			} catch (IOException e) {
				logger.equals("failed to load property file.");
			}
		}
		value = properties.getProperty(key);
		return value;
	}
}
