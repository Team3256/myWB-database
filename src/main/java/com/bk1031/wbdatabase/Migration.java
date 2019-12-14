package com.bk1031.wbdatabase;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Migration {

    static void v1(Connection db) throws SQLException {
        // Users table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'user');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE USER ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"user\" (\n" +
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
                            "    \"discord_id\" text,\n" +
                            "    \"discord_auth_token\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED USER TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // Permission table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'permission');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE PERMISSION ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"permission\" (\n" +
                            "    \"user_id\" text,\n" +
                            "    \"perm\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED PERMISSION TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // Subteam table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'subteam');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE SUBTEAM ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"subteam\" (\n" +
                            "    \"user_id\" text,\n" +
                            "    \"subteam\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED SUBTEAM TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // Attendance table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'attendance');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE ATTENDANCE ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"attendance\" (\n" +
                            "    \"user_id\" text,\n" +
                            "    \"event_id\" text,\n" +
                            "    \"check_in\" timestamp,\n" +
                            "    \"check_out\" timestamp\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED ATTENDANCE TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // Event table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'event');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE EVENT ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"event\" (\n" +
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
                    System.out.println("CREATED EVENT TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // Post table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'post');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE EVENT ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"post\" (\n" +
                            "     \"id\" text,\n" +
                            "     \"title\" text,\n" +
                            "     \"date\" date,\n" +
                            "     \"body\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED POST TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
