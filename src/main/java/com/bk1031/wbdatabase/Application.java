package com.bk1031.wbdatabase;

import com.bk1031.wbdatabase.controller.*;
import com.bk1031.wbdatabase.model.StandardResponse;
import com.bk1031.wbdatabase.model.Token;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sun.mail.smtp.SMTPTransport;
import io.netty.util.Constant;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

	public static void main(String[] args) throws SQLException, IOException, FirebaseMessagingException, MessagingException {
		// Start Spark Webserver
//		secure(Constants.keyStoreLocation, Constants.keyStorePassword, null, null);
		port(6000);
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
		FirebaseMessaging.getInstance().send(Message.builder().setTopic("DEV").setNotification(Notification.builder().setTitle("hello world").setBody("server be online").build()).build());

		app.getAllKeys(db);

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
			return "OK";
		});
		// Check authentication and log
		before((request, response) -> {
			System.out.println();
			System.out.println(new Date());
			System.out.println("REQUESTED ROUTE: " + request.url() + " [" + request.requestMethod() + "]");
			System.out.println("REQUEST BODY: " + request.body());
			System.out.println("REQUEST ORIGIN: " + request.host() + " [" + request.ip() + "]");
			if (!request.requestMethod().equals("OPTIONS")) {
				if (request.headers("Authorization") != null) {
					boolean authenticated = false;
					System.out.println(request.headers("Authorization"));
					String key = request.headers("Authorization").split("Basic ")[1];
					for (int i = 0; i < Constants.apiKeys.size(); i++) {
						if (Constants.apiKeys.get(i).getId().equals(key)) {
							// Authenticated!
							authenticated = true;
							FirebaseDatabase.getInstance().getReference("tokens/" + key).setValueAsync(null);
						}
					}
					if (!authenticated) {
						StandardResponse res = new StandardResponse("ERROR", new Date(), "Invalid authentication token", null);
						System.out.println("INVALID AUTHENTICATION!");
						halt(401, res.toString());
					}
				}
				else {
					StandardResponse res = new StandardResponse("ERROR", new Date(), "Request not authenticated", null);
					System.out.println("NOT AUTHENTICATED!");
					halt(401, res.toString());
				}
			}
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Headers", "*");
			response.type("application/json");
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
		UserController userController = new UserController();
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
			Token token = new Token(rs.getString("id"), rs.getInt("permission"), rs.getTimestamp("created"));
			System.out.println(token);
			Constants.apiKeys.add(token);
		}
		rs.close();
		FirebaseDatabase.getInstance().getReference("tokens").addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {
				Token token = new Token(dataSnapshot.getValue().toString(), 10, new Date());
				System.out.println("NEW AUTH TOKEN: " + token.toString());
				Constants.apiKeys.add(token);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String s) {}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				Constants.apiKeys.removeIf(p -> p.getId().equals(dataSnapshot.getValue().toString()));
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

			@Override
			public void onCancelled(DatabaseError databaseError) {}
		});
	}

}
