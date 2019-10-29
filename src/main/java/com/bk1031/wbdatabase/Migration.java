package com.bk1031.wbdatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Migration {

    static void v1(Connection db) throws SQLException {
        // Users table
        try {
            String sql = "SELECT count(*) FROM \"user\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            if (e.getLocalizedMessage().contains("does not exist")) {
                // Create entity now
                String sql = "CREATE TABLE \"user\" (\n" +
                        "    \"id\" text,\n" +
                        "    \"first_name\" text,\n" +
                        "    \"last_name\" text,\n" +
                        "    \"email\" text,\n" +
                        "    \"phone\" text,\n" +
                        "    \"grade\" integer,\n" +
                        "    \"role\" text,\n" +
                        "    \"varsity\" boolean,\n" +
                        "    \"shirt_size\" text,\n" +
                        "    \"jacket_size\" text,\n" +
                        "    \"discord_id\" text\n" +
                        ");";
                db.createStatement().execute(sql);
            }
        }
        // Permission table
        try {
            String sql = "SELECT count(*) FROM \"permission\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            if (e.getLocalizedMessage().contains("does not exist")) {
                // Create entity now
                String sql = "CREATE TABLE \"permission\" (\n" +
                        "    \"user_id\" text,\n" +
                        "    \"perm\" text\n" +
                        ");";
                db.createStatement().execute(sql);
            }
        }
        // Subteam table
        try {
            String sql = "SELECT count(*) FROM \"subteam\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            if (e.getLocalizedMessage().contains("does not exist")) {
                // Create entity now
                String sql = "CREATE TABLE \"subteam\" (\n" +
                        "    \"user_id\" text,\n" +
                        "    \"subteam\" text\n" +
                        ");";
                db.createStatement().execute(sql);
            }
        }
        // Attendance table
        try {
            String sql = "SELECT count(*) FROM \"attendance\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            if (e.getLocalizedMessage().contains("does not exist")) {
                // Create entity now
                String sql = "CREATE TABLE \"attendance\" (\n" +
                        "    \"user_id\" text,\n" +
                        "    \"event_id\" text,\n" +
                        "    \"check_in\" timestamp,\n" +
                        "    \"check_out\" timestamp\n" +
                        ");";
                db.createStatement().execute(sql);
            }
        }
        // Event table
        try {
            String sql = "SELECT count(*) FROM \"event\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            if (e.getLocalizedMessage().contains("does not exist")) {
                // Create entity now
                String sql = "\n" +
                        "CREATE TABLE \"event\" (\n" +
                        "     \"id\" text,\n" +
                        "     \"date\" date,\n" +
                        "     \"start_time\" timestamp,\n" +
                        "     \"end_time\" timestamp,\n" +
                        "     \"type\" text,\n" +
                        "     \"name\" text,\n" +
                        "     \"desc\" text,\n" +
                        "     \"latitude\" double precision,\n" +
                        "     \"longitude\" double precision,\n" +
                        "     \"radius\" integer\n" +
                        ");";
                db.createStatement().execute(sql);
            }
        }
    }
}
