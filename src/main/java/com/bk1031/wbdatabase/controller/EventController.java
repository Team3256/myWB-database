package com.bk1031.wbdatabase.controller;

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
                event.setDesc(rs.getString("desc"));
                returnList.add(event);
            }
            rs.close();
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
    }

}
