package com.bk1031.wbdatabase;

import com.bk1031.wbdatabase.controller.AttendanceController;
import com.bk1031.wbdatabase.controller.EventController;
import com.bk1031.wbdatabase.controller.PostController;
import com.bk1031.wbdatabase.controller.UserController;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static spark.Spark.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.Date;
import java.util.Properties;

public class Application {

	public static void main(String[] args) throws SQLException {
		// Start Spark Webserver
		port(8081);
		init();
        // Connect to Postgres DB
		Application app = new Application();
		Connection db = app.connect();
		db.setAutoCommit(false);
		// Initialize DB
		Migration.v1(db);
//		String basePath = new File("").getAbsolutePath();
//		if (!basePath.endsWith("/wb-database")) {
//			basePath += "/wb-database";
//		}
//		try {
//			// Initialize object for ScripRunner
//			ScriptRunner sr = new ScriptRunner(db);
//			// Give the input file to Reader
//			Reader reader = new BufferedReader(new FileReader(basePath + Constants.initPath));
//			// Exctute scrpt
//			sr.runScript(reader);
//		} catch (Exception e) {
//			System.err.println("Failed to Execute " + basePath + Constants.initPath
//					+ "\nERROR: " + e.getMessage());
//		}
		// Handle stupid cors shit
		options("/*", (request, response) -> {
			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}
			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}
			response.header("Access-Control-Allow-Headers", "Authorization");
			response.header("Access-Control-Allow-Credentials", "true");
			return "OK";
		});
		// Check authentication and log
		before((request, response) -> {
			// TODO: Check for authentication
			System.out.println();
			System.out.println(new Date());
			System.out.println("REQUESTED ROUTE: " + request.url() + " [" + request.requestMethod() + "]");
			System.out.println("REQUEST BODY: " + request.body());
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
			response.header("Access-Control-Allow-Headers", "Authorization");
			response.header("Access-Control-Allow-Credentials", "true");
			response.header("Content-Type", "application/json");
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
			res.body("{\"message\": \"Hello World!\",\"timestamp\": \"" + new Date().toString() + "\"}");
			return res;
		});
		UserController userController = new UserController(db);
		EventController eventController = new EventController(db);
		AttendanceController attendanceController = new AttendanceController(db);
		PostController postController = new PostController(db);
	}

	public Connection connect() {
		Connection conn = null;
		try {
			Properties props = new Properties();
			props.setProperty("user", Constants.USER);
			props.setProperty("password", Constants.PASSWORD);
			props.setProperty("autosave", "always");
			conn = DriverManager.getConnection(Constants.URL, props);
			System.out.println("Connected to the PostgreSQL server successfully.");
			System.out.println(Constants.URL);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

}
