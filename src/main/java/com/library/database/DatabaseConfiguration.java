package com.library.database;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import lombok.Getter;

@Getter
public class DatabaseConfiguration {
	private String url;
	private String userName;
	private String password;
	private String driverClassName;

	private static final String CONFIG_PATH = "config.database.properties";

	public DatabaseConfiguration() {
		readAndSetConfiguration();
	}
	private void readAndSetConfiguration() {
		try {
			Properties props = new Properties();
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_PATH);
			if (inputStream != null) {
				props.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + CONFIG_PATH + "'is not found");
			}

			this.url = props.getProperty("url");
			this.userName = props.getProperty("username");
			this.password = props.getProperty("password");
			this.driverClassName = props.getProperty("driver-class-name");
			inputStream.close();
		} catch (Exception e) {
			System.out.print("Error | failed in reading configuration file, error=" + e.getMessage());
			e.printStackTrace();
		}
	}
}
