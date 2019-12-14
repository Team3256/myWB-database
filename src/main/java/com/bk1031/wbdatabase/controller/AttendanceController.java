package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Attendance;
import com.google.gson.Gson;
import static spark.Spark.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceController {

    private Connection db;

    Gson gson = new Gson();

    public AttendanceController(Connection db) {
        this.db = db;
        getAttendanceForUser();
        getAttendanceForEvent();
    }

    private void getAttendanceForUser() {
        get("/api/users/:id/attendance", (request, response) -> {
            List<Attendance> returnList = new ArrayList<>();
            // Check if User exists
            String checkSql = "SELECT COUNT(*) FROM \"user\" where id='" + request.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(checkSql);
            while (rs.next()) {
                if (rs.getInt("count") != 1) {
                    response.status(404);
                    response.type("application/json");
                    response.body("{\"message\": \"Requested user not found\"}");
                    return response;
                }
            }
            // Get Attendance
            String attendanceSql = "select * from attendance where user_id='" + request.params(":id") + "'";
            ResultSet rs2 = db.createStatement().executeQuery(attendanceSql);
            while (rs2.next()) {
                Attendance attendance = new Attendance();
                attendance.setUserID(rs2.getString("user_id"));
                attendance.setEventID(rs2.getString("event_id"));
                attendance.setCheckIn(rs2.getTimestamp("check_in"));
                attendance.setCheckOut(rs2.getTimestamp("check_out"));
                returnList.add(attendance);
            }
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
    }

    private void getAttendanceForEvent() {
        get("/api/events/:id/attendance", (request, response) -> {
            List<Attendance> returnList = new ArrayList<>();
            // Check if Event exists
            String checkSql = "SELECT COUNT(*) FROM \"event\" where id='" + request.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(checkSql);
            while (rs.next()) {
                if (rs.getInt("count") != 1) {
                    response.status(404);
                    response.type("application/json");
                    response.body("{\"message\": \"Requested event not found\"}");
                    return response;
                }
            }
            // Get Attendance
            String attendanceSql = "select * from attendance where event_id='" + request.params(":id") + "'";
            ResultSet rs2 = db.createStatement().executeQuery(attendanceSql);
            while (rs2.next()) {
                Attendance attendance = new Attendance();
                attendance.setUserID(rs2.getString("user_id"));
                attendance.setEventID(rs2.getString("event_id"));
                attendance.setCheckIn(rs2.getTimestamp("check_in"));
                attendance.setCheckOut(rs2.getTimestamp("check_out"));
                returnList.add(attendance);
            }
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
    }

}
