package com.bk1031.wbdatabase;

import com.bk1031.wbdatabase.controller.UserController;
import org.apache.ibatis.jdbc.ScriptRunner;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static spark.Spark.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class Application {

	public static void main(String[] args) throws SQLException {
		// Start Spark Webserver
		init();
        // Connect to Postgres DB
		Application app = new Application();
		Connection db = app.connect();
		// Initialize DB
		String basePath = new File("").getAbsolutePath();
		try {
			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(db);
			// Give the input file to Reader
			Reader reader = new BufferedReader(new FileReader(basePath + Constants.initPath));
			// Exctute scrpt
			sr.runScript(reader);

		} catch (Exception e) {
			System.err.println("Failed to Execute " + Constants.initPath
					+ "\nERROR: " + e.getMessage());
		}
		// Initialize Object Controllers
		before("/api/", ((request, response) -> System.out.println(request.url())));
		get("/api/test", (req, res) -> "Hello World");
		UserController userController = new UserController(db);
	}

	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

}
