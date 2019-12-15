package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Attendance;
import com.bk1031.wbdatabase.model.Event;
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
                event.setStartTime(rs.getTimestamp("start_time"));
                event.setEndTime(rs.getTimestamp("end_time"));
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
                event.setStartTime(rs.getTimestamp("start_time"));
                event.setEndTime(rs.getTimestamp("end_time"));
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
}