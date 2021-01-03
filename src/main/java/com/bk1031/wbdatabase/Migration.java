package com.bk1031.wbdatabase;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Migration {

    static void v1(Connection db) throws SQLException {
        // API KEY table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'api_key');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE API_KEY ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"api_key\" (\n" +
                            "     \"id\" text,\n" +
                            "     \"permission\" integer,\n" +
                            "     \"created\" timestamp" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED API_KEY TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
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
                            "    \"gender\" text,\n" +
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
        // Role table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'role');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE ROLE ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"role\" (\n" +
                            "    \"user_id\" text,\n" +
                            "    \"role\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED ROLE TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
