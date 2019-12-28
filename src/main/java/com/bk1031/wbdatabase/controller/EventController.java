package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Attendance;
import com.bk1031.wbdatabase.model.Event;
import com.bk1031.wbdatabase.model.User;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class EventController {

    private Connection db;

    Gson gson = new Gson();

    public EventController(Connection db) {
        this.db = db;
        getAllEvents();
        getEvent();
        createEvent();
        updateEvent();
    }

    private void getAllEvents() {
        get("/api/events", (request, response) -> {
            List<Event> returnList = new ArrayList<>();
            // Get Events
            String sql = "SELECT * FROM \"event\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getString("id"));
                event.setDate(rs.getDate("date"));
                event.setStartTime(rs.getTimestamp("start_time").toString());
                event.setEndTime(rs.getTimestamp("end_time").toString());
                event.setType(rs.getString("type"));
                event.setName(rs.getString("name"));
                event.setDesc(rs.getString("desc"));
                event.setLatitude(rs.getDouble("latitude"));
                event.setLongitude(rs.getDouble("longitude"));
                event.setRadius(rs.getInt("radius"));
                returnList.add(event);
            }
            rs.close();
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
    }

    private void getEvent() {
        get("/api/events/:id", (request, response) -> {
            // Get Event
            Event event = new Event();
            String sql = "SELECT * FROM \"event\" WHERE id='" + request.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                event.setId(rs.getString("id"));
                event.setDate(rs.getDate("date"));
                event.setStartTime(rs.getTimestamp("start_time").toString());
                event.setEndTime(rs.getTimestamp("end_time").toString());
                event.setType(rs.getString("type"));
                event.setName(rs.getString("name"));
                event.setDesc(rs.getString("desc"));
                event.setLatitude(rs.getDouble("latitude"));
                event.setLongitude(rs.getDouble("longitude"));
                event.setRadius(rs.getInt("radius"));
            }
            rs.close();
            if (event.toString().contains("null")) {
                response.status(404);
                response.type("application/json");
                response.body("{\"message\": \"Requested event not found\"}");
                return response;
            }
            response.type("application/json");
            response.body(event.toString());
            return response;
        });
    }

    private void createEvent() {
        post("/api/events", (req, res) -> {
            Event event = gson.fromJson(req.body(), Event.class);
            System.out.println("PARSED EVENT: " + event);
            if (event.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            String existsSql = "SELECT COUNT(1) FROM \"event\" WHERE id = '" + event.getId() + "'";
            ResultSet rs = db.createStatement().executeQuery(existsSql);
            while (rs.next()) {
                if (rs.getInt("count") == 1) {
                    res.status(409);
                    res.type("application/json");
                    res.body("{\"message\": \"Event already exists with id: " + event.getId() + "\"}");
                    return res;
                }
            }
            String sql = "INSERT INTO \"event\" VALUES " +
                    "(" +
                    "'" + event.getId() + "'," +
                    "'" + event.getDate() + "'," +
                    "'" + event.getStartTime() + "'," +
                    "'" + event.getEndTime() + "'," +
                    "'" + event.getType() + "'," +
                    "'" + event.getName() + "'," +
                    "'" + event.getDesc() + "'," +
                    event.getLatitude() + "," +
                    event.getLongitude() + "," +
                    event.getRadius() +
                    ")";
            db.createStatement().executeUpdate(sql);
            db.commit();
            System.out.println("Inserted records into the table...");
            res.type("application/json");
            res.body(event.toString());
            return res;
        });
    }

    private void updateEvent() {
        put("/api/events/:id", (req, res) -> {
            Event event = gson.fromJson(req.body(), Event.class);
            event.setId(req.params(":id"));
            System.out.println("PARSED EVENT: " + event);
            if (event.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            String existsSql = "SELECT COUNT(1) FROM \"event\" WHERE id = '" + event.getId() + "'";
            ResultSet rs = db.createStatement().executeQuery(existsSql);
            while (rs.next()) {
                if (rs.getInt("count") != 1) {
                    res.status(400);
                    res.type("application/json");
                    res.body("{\"message\": \"No mapping for given id: " + event.getId() + "\"}");
                    return res;
                }
            }
            String sql = "UPDATE \"event\" SET " +
                    "date='" + event.getDate() + "'," +
                    "start_time='" + event.getStartTime() + "'," +
                    "end_time='" + event.getEndTime() + "'," +
                    "type='" + event.getType() + "'," +
                    "name='" + event.getName() + "'," +
                    "\"desc\"='" + event.getDesc() + "'," +
                    "latitude=" + event.getLatitude() + "," +
                    "longitude=" + event.getLongitude() + "," +
                    "radius=" + event.getRadius() + " " +
                    "WHERE id='" + event.getId() + "'";
            db.createStatement().executeUpdate(sql);
            db.commit();
            System.out.println("Inserted records into the table...");
            res.type("application/json");
            res.body(event.toString());
            return res;
        });
    }
}
