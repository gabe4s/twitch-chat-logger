package com.gwdedb.twitchchatlogger.server;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigValues {

	private static final File CONFIG_FILE = new File(System.getProperty("user.home") + "/loggerconfig/config.properties");
	private static Properties properties = null;
	
	public static String getProperty(String key) {
		String property = null;
		if(properties == null) {
			properties = new Properties();
			try {
				FileInputStream fis = new FileInputStream(CONFIG_FILE);
				properties.load(fis);
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		property = properties.getProperty(key);
		return property;
	}
	
}
