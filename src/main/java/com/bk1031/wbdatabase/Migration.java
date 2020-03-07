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
                            "    \"check_out\" timestamp,\n" +
                            "    \"status\" text\n" +
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
                    System.out.println("TABLE POST ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"post\" (\n" +
                            "     \"id\" text,\n" +
                            "     \"author_id\" text,\n" +
                            "     \"title\" text,\n" +
                            "     \"date\" timestamp,\n" +
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
        // Post_Tags table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'post_tag');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE POST_TAG ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"post_tag\" (\n" +
                            "     \"post_id\" text,\n" +
                            "     \"tag\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED POST_TAG TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // Excused Attendance Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'excused_attendance');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE EXCUSED ATTENDANCE ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"excused_attendance\" (\n" +
                            "     \"user_id\" text,\n" +
                            "     \"event_id\" text,\n" +
                            "     \"status\" text,\n" +
                            "     \"reason\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED EXCUSED ATTENDANCE TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
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
                            "     \"level\" integer\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED API_KEY TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // PURCHASE REQUEST Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'purchase_request');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE PURCHASE REQUEST ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"purchase_request\" (\n" +
                            "     \"id\" text,\n" +
                            "     \"is_sheet\" bool,\n" +
                            "     \"user_id\" text,\n" +
                            "     \"part_name\" text,\n" +
                            "     \"part_quantity\" integer, \n" +
                            "     \"part_url\" text, \n" +
                            "     \"vendor\" text, \n" +
                            "     \"need_by\" date, \n" +
                            "     \"submitted_on\" date, \n" +
                            "     \"part_number\" text, \n" +
                            "     \"cost\" double precision, \n" +
                            "     \"total_cost\" double precision, \n" +
                            "     \"justification\" text, \n" +
                            "     \"status\" text \n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED PURCHASE REQUEST TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // CART Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'cart');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE CART ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"cart\" (\n" +
                            "     \"product_id\" text,\n" +
                            "     \"user_id\" text,\n" +
                            "     \"product_name\" text,\n" +
                            "     \"size\" text,\n" +
                            "     \"variant\" text,\n" +
                            "     \"quantity\" integer,\n" +
                            "     \"price\" integer \n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED CART TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // ORDER_ITEM Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'order_item');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE ORDER_ITEM ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"order_item\" (\n" +
                            "     \"product_id\" text,\n" +
                            "     \"order_id\" text,\n" +
                            "     \"product_name\" text,\n" +
                            "     \"size\" text,\n" +
                            "     \"variant\" text,\n" +
                            "     \"quantity\" integer,\n" +
                            "     \"price\" integer \n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED ORDER_ITEM TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // ORDER Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'order');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE ORDER ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"order\" (\n" +
                            "     \"order_id\" text,\n" +
                            "     \"user_id\" text,\n" +
                            "     \"payment_complete\" bool\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED ORDER TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // REGIONAL Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'regional');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE REGIONAL ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"regional\" (\n" +
                            "     \"id\" text,\n" +
                            "     \"city\" text,\n" +
                            "     \"country\" text,\n" +
                            "     \"start_date\" date,\n" +
                            "     \"end_date\" date,\n" +
                            "     \"year\" integer,\n" +
                            "     \"short_name\" text,\n" +
                            "     \"name\" text,\n" +
                            "     \"event_code\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED REGIONAL TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // TEAM Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'team');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE TEAM ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"team\" (\n" +
                            "     \"id\" text,\n" +
                            "     \"nickname\" text,\n" +
                            "     \"name\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED TEAM TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // REGIONAL_TEAM Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'regional_team');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE REGIONAL_TEAM ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"regional_team\" (\n" +
                            "     \"regional_id\" text,\n" +
                            "     \"team_id\" text\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED REGIONAL_TEAM TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // MATCH Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'match');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE MATCH ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"match\" (\n" +
                            "     \"id\" text,\n" +
                            "     \"regional_id\" text,\n" +
                            "     \"match_num\" integer\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED MATCH TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // MATCH_TEAM Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'match_team');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE MATCH_TEAM ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"match_team\" (\n" +
                            "     \"match_id\" text,\n" +
                            "     \"team_id\" text,\n" +
                            "     \"scouter_id\" text,\n" +
                            "     \"alliance\" text,\n" +
                            "     \"preload\" integer,\n" +
                            "     \"level\" boolean,\n" +
                            "     \"park\" boolean\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED MATCH TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // AUTO Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'auto');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE AUTO ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"auto\" (\n" +
                            "     \"match_id\" text,\n" +
                            "     \"team_id\" text,\n" +
                            "     \"start_position\" text,\n" +
                            "     \"crossed\" boolean,\n" +
                            "     \"cross_time\" double precision,\n" +
                            "     \"trench\" boolean\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED AUTO TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // POWER_CELL Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'power_cell');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE POWER_CELL ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"power_cell\" (\n" +
                            "     \"match_id\" text,\n" +
                            "     \"team_id\" text,\n" +
                            "     \"drop_location\" text,\n" +
                            "     \"pickup_time\" double precision,\n" +
                            "     \"cycle_time\" double precision\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED POWER_CELL TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // SPIN Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'spin');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE SPIN ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"spin\" (\n" +
                            "     \"match_id\" text,\n" +
                            "     \"team_id\" text,\n" +
                            "     \"rotation\" boolean,\n" +
                            "     \"rotation_time\" double precision ,\n" +
                            "     \"position\" boolean,\n" +
                            "     \"position_time\" double precision \n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED SPIN TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // CLIMB Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'climb');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE CLIMB ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"climb\" (\n" +
                            "     \"match_id\" text,\n" +
                            "     \"team_id\" text,\n" +
                            "     \"start_time\" double precision ,\n" +
                            "     \"climb_time\" double precision ,\n" +
                            "     \"dropped\" boolean\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED CLIMB TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
        // DISCONNECT Table
        try {
            String sql = "SELECT EXISTS (SELECT 1 FROM pg_tables WHERE schemaname = 'public' AND tablename = 'disconnect');";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                if (rs.getBoolean("exists")) {
                    System.out.println("TABLE DISCONNECT ALREADY EXISTS!");
                }
                else {
                    sql = "CREATE TABLE \"disconnect\" (\n" +
                            "     \"match_id\" text,\n" +
                            "     \"team_id\" text,\n" +
                            "     \"start_time\" double precision ,\n" +
                            "     \"duration\" double precision\n" +
                            ");";
                    db.createStatement().execute(sql);
                    System.out.println("CREATED DISCONNECT TABLE");
                    db.commit();
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}
