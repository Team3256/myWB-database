package com.bk1031.wbdatabase;

import com.bk1031.wbdatabase.controller.*;
import com.bk1031.wbdatabase.model.Post;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import io.netty.util.Constant;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class Application {

	public static void main(String[] args) throws SQLException, IOException, FirebaseMessagingException {
		// Start Spark Webserver
		port(8081);
		init();
        // Connect to Postgres DB
		Application app = new Application();
		Connection db = app.connect();
		db.setAutoCommit(false);
		// Initialize DB
		Migration.v1(db);

		// Firebase admin time
		FileInputStream serviceAccount = new FileInputStream("src/main/resources/serviceAccountKey.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://mywb-app.firebaseio.com")
				.build();

		FirebaseApp.initializeApp(options);

		FirebaseDatabase.getInstance().getReference("testing").push().setValueAsync("server be online");
		FirebaseMessaging.getInstance().send(Message.builder().setTopic("DEV").setNotification(Notification.builder().setTitle("test").setBody("hello world").build()).build());

		app.getAllKeys(db);

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
			System.out.println();
			System.out.println(new Date());
			System.out.println("REQUESTED ROUTE: " + request.url() + " [" + request.requestMethod() + "]");
			System.out.println("REQUEST BODY: " + request.body());
			if (!request.requestMethod().equals("OPTIONS")) {
				if (request.headers("Authentication") != null) {
					boolean authenticated = false;
					System.out.println(request.headers("Authentication"));
					String key = request.headers("Authentication").split("Bearer ")[1];
					for (int i = 0; i < Constants.apiKeys.size(); i++) {
						if (Constants.apiKeys.get(i).equals(key)) {
							// Authenticated!
							authenticated = true;
						}
					}
					if (!authenticated) {
						System.out.println("INVALID API KEY!");
						halt(401, "{\"message\": \"Invalid API Key\"}");
					}
				}
				else {
					System.out.println("NOT AUTHENTICATED!");
					halt(401, "{\"message\": \"Request not authorized\"}");
				}
			}
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
		PurchaseRequestController purchaseRequestController = new PurchaseRequestController(db);
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

	public void getAllKeys(Connection db) throws SQLException {
		String sql = "SELECT id FROM \"api_key\"";
		ResultSet rs = db.createStatement().executeQuery(sql);
		while(rs.next()) {
			System.out.println(rs.getString("id"));
			Constants.apiKeys.add(rs.getString("id"));
		}
		rs.close();
//		get("/api/keys", (request, response) -> {
//			Constants.apiKeys.clear();
//			ResultSet rs2 = db.createStatement().executeQuery(sql);
//			String returnString = "[";
//			while(rs2.next()) {
//				System.out.println(rs2.getString("id"));
//				Constants.apiKeys.add(rs2.getString("id"));
//				returnString += "\"" + rs2.getString("id") + "\"";
//				if (rs2.next()) {
//					returnString += ",";
//				}
//			}
//			System.out.println(Constants.apiKeys);
//			returnString += "]";
//			rs2.close();
//			response.body(returnString);
//			return response;
//		});
	}

}
