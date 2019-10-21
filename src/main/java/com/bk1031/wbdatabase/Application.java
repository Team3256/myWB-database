package com.bk1031.wbdatabase;

import com.bk1031.wbdatabase.controller.EventController;
import com.bk1031.wbdatabase.controller.UserController;
import org.apache.ibatis.jdbc.ScriptRunner;
import spark.Spark;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static spark.Spark.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.Date;

public class Application {

	public static void main(String[] args) throws SQLException {
		// Start Spark Webserver
		init();
        // Connect to Postgres DB
		Application app = new Application();
		Connection db = app.connect();
		// Initialize DB
		String basePath = new File("").getAbsolutePath();
		if (!basePath.endsWith("/wb-database")) {
			basePath += "/wb-database";
		}
		try {
			// Initialize object for ScripRunner
			ScriptRunner sr = new ScriptRunner(db);
			// Give the input file to Reader
			Reader reader = new BufferedReader(new FileReader(basePath + Constants.initPath));
			// Exctute scrpt
			sr.runScript(reader);
		} catch (Exception e) {
			System.err.println("Failed to Execute " + basePath + Constants.initPath
					+ "\nERROR: " + e.getMessage());
		}
		// Check authentication
		before((request, response) -> {
			// TODO: Check for authentication
			System.out.println(new Date());
			System.out.println("REQUESTED ROUTE: " + request.url());
			System.out.println("REQUEST BODY: " + request.body());
		});
		// Initialize request logging
		after((request, response) -> {
			System.out.println("RESPONSE CODE: " + response.status());
			System.out.println("RESPONSE BODY: " + response.body());
			System.out.println();
		});
		// Initialize Object Controllers
		get("/api/test", (req, res) -> {
			res.type("application/json");
			res.body("{\"message\": \"Hello World!\"}");
			return res;
		});
		UserController userController = new UserController(db);
		EventController eventController = new EventController(db);
	}

	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
			System.out.println("Connected to the PostgreSQL server successfully.");
			System.out.println(Constants.url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

}
