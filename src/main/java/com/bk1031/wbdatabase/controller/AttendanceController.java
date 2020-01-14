package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Attendance;
import com.bk1031.wbdatabase.model.ExcusedAttendance;
import com.google.gson.Gson;
import static spark.Spark.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
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
        getExcusedAttendanceForUser();
        createExcusedAbsence();
        getAttendanceForUserForEvent();
        getAttendanceForEvent();
        createAttendance();
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
                attendance.setCheckIn(rs2.getTimestamp("check_in").toString());
                attendance.setCheckOut(rs2.getTimestamp("check_out").toString());
                attendance.setStatus(rs2.getString("status"));
                Timestamp t1 = rs2.getTimestamp("check_in");
                Timestamp t2 = rs2.getTimestamp("check_out");
                double milliseconds = t2.getTime() - t1.getTime();
                attendance.setHours(milliseconds / 3600000);
                // Get type for event
                String typeSql = "SELECT type FROM \"event\" WHERE id='" + attendance.getEventID()+ "'";
                ResultSet rs3 = db.createStatement().executeQuery(typeSql);
                while (rs3.next()) {
                    attendance.setType(rs3.getString("type"));
                }
                returnList.add(attendance);
            }
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
    }

    private void getExcusedAttendanceForUser() {
        get("/api/users/:id/attendance/excused", (request, response) -> {
            List<ExcusedAttendance> returnList = new ArrayList<>();
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
            String attendanceSql = "select * from excused_attendance where user_id='" + request.params(":id") + "'";
            ResultSet rs2 = db.createStatement().executeQuery(attendanceSql);
            while (rs2.next()) {
                ExcusedAttendance attendance = new ExcusedAttendance();
                attendance.setUserID(rs2.getString("user_id"));
                attendance.setEventID(rs2.getString("event_id"));
                attendance.setStatus(rs2.getString("status"));
                attendance.setReason(rs2.getString("reason"));
                returnList.add(attendance);
            }
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
    }

    private void createExcusedAbsence() {
        post("/api/users/:id/attendance/excused", (request, response) -> {
            ExcusedAttendance attendance = gson.fromJson(request.body(), ExcusedAttendance.class);
            attendance.setUserID(request.params(":id"));
            System.out.println("PARSED EXCUSED ATTENDANCE: " + attendance);
            if (attendance.toString().contains("null")) {
                response.status(400);
                response.type("application/json");
                response.body("{\"message\": \"Request missing or contains null values\"}");
                return response;
            }
            // Check if Event exists
            String checkSql = "SELECT COUNT(*) FROM \"event\" where id='" + attendance.getEventID() + "'";
            ResultSet rs = db.createStatement().executeQuery(checkSql);
            while (rs.next()) {
                if (rs.getInt("count") != 1) {
                    response.status(404);
                    response.type("application/json");
                    response.body("{\"message\": \"Requested event not found\"}");
                    return response;
                }
            }
            // Check if entry aleady exists for given event and user
            String existsSql = "SELECT COUNT(*) FROM \"excused_attendance\" WHERE user_id='" + attendance.getUserID() + "' AND event_id='" + attendance.getEventID() + "'";
            ResultSet rs2 = db.createStatement().executeQuery(existsSql);
            while (rs2.next()) {
                if (rs2.getInt("count") != 1) {
                    String sql = "INSERT INTO \"excused_attendance\" VALUES " +
                            "(" +
                            "'" + attendance.getUserID() + "'," +
                            "'" + attendance.getEventID() + "'," +
                            "'" + attendance.getStatus() + "'," +
                            "'" + attendance.getReason() + "'" +
                            ")";
                    db.createStatement().executeUpdate(sql);
                    db.commit();
                    System.out.println("Inserted records into the table...");
                    response.type("application/json");
                    response.body(attendance.toString());
                    return response;
                }
                else {
                    // Update existing attendance
                    String sql = "UPDATE \"excused_attendance\" SET " +
                            "reason='" + attendance.getReason() + "'," +
                            "status='" + attendance.getStatus() + "'" +
                            "WHERE user_id='" + attendance.getUserID() + "' AND event_id='" + attendance.getEventID() + "'";
                    db.createStatement().executeUpdate(sql);
                    db.commit();
                    System.out.println("Updated records in the table...");
                    response.type("application/json");
                    response.body(attendance.toString());
                    return response;
                }
            }
            response.status(500);
            response.type("application/json");
            response.body("{\"message\": \"Something did the funny thing, check server logs\"}");
            return response;
        });
    }

    private void getAttendanceForUserForEvent() {
        get("/api/users/:id/attendance/:eid", (request, response) -> {
            Attendance attendance = new Attendance();
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
            // Check if Event exists
            String checkEventSql = "SELECT COUNT(*) FROM \"event\" where id='" + request.params(":eid") + "'";
            ResultSet rs2 = db.createStatement().executeQuery(checkEventSql);
            while (rs2.next()) {
                if (rs2.getInt("count") != 1) {
                    response.status(404);
                    response.type("application/json");
                    response.body("{\"message\": \"Requested event not found\"}");
                    return response;
                }
            }
            // Check if entry aleady exists for given event and user
            String existsSql = "SELECT COUNT(*) FROM \"attendance\" WHERE user_id='" + request.params(":id") + "' AND event_id='" + request.params(":eid") + "'";
            ResultSet rs3 = db.createStatement().executeQuery(existsSql);
            while (rs3.next()) {
                if (rs3.getInt("count") != 1) {
                    response.status(404);
                    response.type("application/json");
                    response.body("{\"message\": \"Requested attendance entry not found\"}");
                    return response;
                }
            }
            // Get attendance
            String attendanceSql = "select * from attendance where user_id='" + request.params(":id") + "' AND event_id='" + request.params(":eid") + "'";
            ResultSet rs4 = db.createStatement().executeQuery(attendanceSql);
            while (rs4.next()) {
                attendance.setUserID(rs4.getString("user_id"));
                attendance.setEventID(rs4.getString("event_id"));
                attendance.setCheckIn(rs4.getTimestamp("check_in").toString());
                attendance.setCheckOut(rs4.getTimestamp("check_out").toString());
                attendance.setStatus(rs4.getString("status"));
                Timestamp t1 = rs4.getTimestamp("check_in");
                Timestamp t2 = rs4.getTimestamp("check_out");
                double milliseconds = t2.getTime() - t1.getTime();
                attendance.setHours(milliseconds / 3600000);
            }
            // Get type for event
            String typeSql = "SELECT type FROM \"event\" WHERE id='" + request.params(":eid") + "'";
            ResultSet rs5 = db.createStatement().executeQuery(typeSql);
            while (rs5.next()) {
                attendance.setType(rs5.getString("type"));
            }
            response.type("application/json");
            response.body(attendance.toString());
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
                attendance.setCheckIn(rs2.getTimestamp("check_in").toString());
                attendance.setCheckOut(rs2.getTimestamp("check_out").toString());
                attendance.setStatus(rs2.getString("status"));
                Timestamp t1 = rs2.getTimestamp("check_in");
                Timestamp t2 = rs2.getTimestamp("check_out");
                double milliseconds = t2.getTime() - t1.getTime();
                attendance.setHours(milliseconds / 3600000);
                // Get type for event
                String typeSql = "SELECT type FROM \"event\" WHERE id='" + attendance.getEventID()+ "'";
                ResultSet rs3 = db.createStatement().executeQuery(typeSql);
                while (rs3.next()) {
                    attendance.setType(rs3.getString("type"));
                }
                returnList.add(attendance);
            }
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
    }

    private void createAttendance() {
        post("/api/events/:id/attendance", (request, response) -> {
            Attendance attendance = gson.fromJson(request.body(), Attendance.class);
            attendance.setEventID(request.params(":id"));
            attendance.setType("");
            System.out.println("PARSED ATTENDANCE: " + attendance);
            if (attendance.toString().contains("null")) {
                response.status(400);
                response.type("application/json");
                response.body("{\"message\": \"Request missing or contains null values\"}");
                return response;
            }
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
                else {
                    // Get type for event
                    String typeSql = "SELECT type FROM \"event\" WHERE id='" + attendance.getEventID()+ "'";
                    ResultSet rs3 = db.createStatement().executeQuery(typeSql);
                    while (rs3.next()) {
                        attendance.setType(rs3.getString("type"));
                    }
                }
            }
            // Check if entry aleady exists for given event and user
            String existsSql = "SELECT COUNT(*) FROM \"attendance\" WHERE user_id='" + attendance.getUserID() + "' AND event_id='" + request.params(":id") + "'";
            ResultSet rs2 = db.createStatement().executeQuery(existsSql);
            while (rs2.next()) {
                if (rs2.getInt("count") != 1) {
                    String sql = "INSERT INTO \"attendance\" VALUES " +
                            "(" +
                            "'" + attendance.getUserID() + "'," +
                            "'" + attendance.getEventID() + "'," +
                            "'" + attendance.getCheckIn() + "'," +
                            "'" + attendance.getCheckOut() + "'," +
                            "'present'" +
                            ")";
                    db.createStatement().executeUpdate(sql);
                    db.commit();
                    double milliseconds = Timestamp.valueOf(attendance.getCheckOut()).getTime() - Timestamp.valueOf(attendance.getCheckIn()).getTime();
                    attendance.setHours(milliseconds / 3600000);
                    System.out.println("Inserted records into the table...");
                    response.type("application/json");
                    response.body(attendance.toString());
                    return response;
                }
                else {
                    // Update existing attendance
                    String sql = "UPDATE \"attendance\" SET " +
                            "check_in='" + attendance.getCheckIn() + "'," +
                            "check_out='" + attendance.getCheckOut() + "'," +
                            "status='" + attendance.getStatus() + "'" +
                            "WHERE user_id='" + attendance.getUserID() + "' AND event_id='" + attendance.getEventID() + "'";
                    db.createStatement().executeUpdate(sql);
                    db.commit();
                    double milliseconds = Timestamp.valueOf(attendance.getCheckOut()).getTime() - Timestamp.valueOf(attendance.getCheckIn()).getTime();
                    attendance.setHours(milliseconds / 3600000);
                    System.out.println("Updated records in the table...");
                    response.type("application/json");
                    response.body(attendance.toString());
                    return response;
                }
            }
            response.status(500);
            response.type("application/json");
            response.body("{\"message\": \"Something did the funny thing, check server logs\"}");
            return response;
        });
    }

}