package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.User;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class UserController {

    private Connection db;

    Gson gson = new Gson();

    public UserController(Connection db) {
        this.db = db;
        getAllUsers();
        getUser();
        createUser();
        updateUser();
    }

    private void getAllUsers() {
        get("/api/users", (req, res) -> {
            List<User> returnList = new ArrayList<>();
            // Get Users
            String sql = "SELECT * FROM \"user\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setGrade(rs.getInt("grade"));
                user.setRole(rs.getString("role"));
                user.setVarsity(rs.getBoolean("varsity"));
                user.setShirtSize(rs.getString("shirt_size"));
                user.setJacketSize(rs.getString("jacket_size"));
                user.setDiscordID(rs.getString("discord_id"));
                user.setDiscordAuthToken(rs.getString("discord_auth_token"));
                // Get Subteams for User
                String subteamSql = "select subteam.subteam from subteam where subteam.user_id='" + user.getId() + "'";
                ResultSet rs2 = db.createStatement().executeQuery(subteamSql);
                while (rs2.next()) {
                    user.subteams.add(rs2.getString("subteam"));
                }
                // Get Permissions for User
                String permSql = "select perm from permission where permission.user_id='" + user.getId() + "'";
                ResultSet rs3 = db.createStatement().executeQuery(permSql);
                while (rs3.next()) {
                    user.perms.add(rs3.getString("perm"));
                }
                returnList.add(user);
            }
            rs.close();
            res.type("application/json");
            res.body(returnList.toString());
            return res;
        });
    }

    private void getUser() {
        get("/api/users/:id", (req, res) -> {
            User user = new User();
            String sql = "SELECT * FROM \"user\" WHERE id='" + req.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                user.setId(rs.getString("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setGrade(rs.getInt("grade"));
                user.setRole(rs.getString("role"));
                user.setVarsity(rs.getBoolean("varsity"));
                user.setShirtSize(rs.getString("shirt_size"));
                user.setJacketSize(rs.getString("jacket_size"));
                user.setDiscordID(rs.getString("discord_id"));
                user.setDiscordAuthToken(rs.getString("discord_auth_token"));
                // Get Subteams for User
                String subteamSql = "select subteam.subteam from subteam where subteam.user_id='" + user.getId() + "'";
                ResultSet rs2 = db.createStatement().executeQuery(subteamSql);
                while (rs2.next()) {
                    user.subteams.add(rs2.getString("subteam"));
                }
                // Get Permissions for User
                String permSql = "select perm from permission where permission.user_id='" + user.getId() + "'";
                ResultSet rs3 = db.createStatement().executeQuery(permSql);
                while (rs3.next()) {
                    user.perms.add(rs3.getString("perm"));
                }
            }
            rs.close();
            if (user.toString().contains("null")) {
                res.status(404);
                res.type("application/json");
                res.body("{\"message\": \"Requested user not found\"}");
                return res;
            }
            res.type("application/json");
            res.body(user.toString());
            return res;
        });
    }

    private void createUser() {
        post("/api/users", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            System.out.println("PARSED STUDENT: " + user);
            if (user.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            String existsSql = "SELECT COUNT(1) FROM \"user\" WHERE id = '" + user.getId() + "'";
            ResultSet rs = db.createStatement().executeQuery(existsSql);
            while (rs.next()) {
                if (rs.getInt("count") == 1) {
                    res.status(409);
                    res.type("application/json");
                    res.body("{\"message\": \"User already exists with id: " + user.getId() + "\"}");
                    return res;
                }
            }
            String sql = "INSERT INTO \"user\" VALUES " +
                    "(" +
                    "'" + user.getId() + "'," +
                    "'" + user.getFirstName() + "'," +
                    "'" + user.getLastName() + "'," +
                    "'" + user.getEmail() + "'," +
                    "'" + user.getPhone() + "'," +
                    user.getGrade() + "," +
                    "'" + user.getRole() + "'," +
                    user.isVarsity() + "," +
                    "'" + user.getShirtSize() + "'," +
                    "'" + user.getJacketSize() + "'," +
                    "'" + user.getDiscordID() + "'," +
                    "'" + user.getDiscordAuthToken() + "'" +
                    ")";
            db.createStatement().executeUpdate(sql);
            for (String subteam : user.subteams) {
                sql = "INSERT INTO subteam VALUES " +
                        "(" +
                        "'" + user.getId() + "'," +
                        "'" + subteam + "'" +
                        ")";
                db.createStatement().executeUpdate(sql);
            }
            for (String perm : user.perms) {
                sql = "INSERT INTO permission VALUES " +
                        "(" +
                        "'" + user.getId() + "'," +
                        "'" + perm + "'" +
                        ")";
                db.createStatement().executeUpdate(sql);
            }
            db.commit();
            System.out.println("Inserted records into the table...");
            res.type("application/json");
            res.body(user.toString());
            return res;
        });
    }

    private void updateUser() {
        put("/api/users/:id", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            user.setId(req.params(":id"));
            System.out.println("PARSED STUDENT: " + user);
            if (user.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            String existsSql = "SELECT COUNT(1) FROM \"user\" WHERE id = '" + user.getId() + "'";
            ResultSet rs = db.createStatement().executeQuery(existsSql);
            while (rs.next()) {
                if (rs.getInt("count") != 1) {
                    res.status(400);
                    res.type("application/json");
                    res.body("{\"message\": \"No mapping for given id: " + user.getId() + "\"}");
                    return res;
                }
            }
            String sql = "UPDATE \"user\" SET " +
                    "first_name='" + user.getFirstName() + "'," +
                    "last_name='" + user.getLastName() + "'," +
                    "email='" + user.getEmail() + "'," +
                    "phone='" + user.getPhone() + "'," +
                    "grade=" + user.getGrade() + "," +
                    "role='" + user.getRole() + "'," +
                    "varsity=" + user.isVarsity() + "," +
                    "shirt_size='" + user.getShirtSize() + "'," +
                    "jacket_size='" + user.getJacketSize() + "'," +
                    "discord_id='" + user.getDiscordID() + "'," +
                    "discord_auth_token='" + user.getDiscordAuthToken() + "' " +
                    "WHERE id='" + user.getId() + "'";
            db.createStatement().executeUpdate(sql);
            // Clear existing subteam list
            sql = "DELETE FROM \"subteam\" WHERE user_id='" + user.getId() + "'";
            db.createStatement().executeUpdate(sql);
            // Clear existing perms list
            sql = "DELETE FROM \"permission\" WHERE user_id='" + user.getId() + "'";
            db.createStatement().executeUpdate(sql);
            for (String subteam : user.subteams) {
                sql = "INSERT INTO subteam VALUES " +
                        "(" +
                        "'" + user.getId() + "'," +
                        "'" + subteam + "'" +
                        ")";
                db.createStatement().executeUpdate(sql);
            }
            for (String perm : user.perms) {
                sql = "INSERT INTO permission VALUES " +
                        "(" +
                        "'" + user.getId() + "'," +
                        "'" + perm + "'" +
                        ")";
                db.createStatement().executeUpdate(sql);
            }
            db.commit();
            System.out.println("Inserted records into the table...");
            res.type("application/json");
            res.body(user.toString());
            return res;
        });
    }

}
