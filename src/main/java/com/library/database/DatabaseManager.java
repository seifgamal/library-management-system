package com.library.database;

import com.library.controller.AuthorController;
import com.library.database.DatabaseConfiguration;
import java.sql.Connection;
import java.sql.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseManager {

	private Connection connection = null;
	private DatabaseConfiguration dbConfig;
	private static final Logger logger = LogManager.getLogger(AuthorController.class.getSimpleName());

	public DatabaseManager(DatabaseConfiguration dbConfig) {
		this.dbConfig = dbConfig;
		this.connection = this.getConnectionObject();
	}

	public Connection getConnectionObject() {
		if (this.connection != null) {
			return this.connection;
		}

		try {
			Class.forName(dbConfig.getDriverClassName());
			connection = DriverManager
					.getConnection(dbConfig.getUrl(), dbConfig.getUserName(), dbConfig.getPassword());
			return connection;
		} catch (Exception e) {
			logger.error("Failed in opening a connection to the database, error={}", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
