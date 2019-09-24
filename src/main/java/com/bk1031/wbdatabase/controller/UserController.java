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
    }

    private void getAllUsers() {
        get("/api/users", (req, res) -> {
            res.type("application/json");
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
                // Get Subteams for User
                String subteamSql = "select subteam.subteam from subteam where subteam.student_id='" + user.getId() + "'";
                ResultSet rs2 = db.createStatement().executeQuery(subteamSql);
                while (rs2.next()) {
                    user.subteams.add(rs2.getString("subteam"));
                }
                // Get Permissions for User
                String permSql = "select perm from permission where permission.student_id='" + user.getId() + "'";
                ResultSet rs3 = db.createStatement().executeQuery(permSql);
                while (rs3.next()) {
                    user.perms.add(rs3.getString("perm"));
                }
                returnList.add(user);
            }
            rs.close();
            String noob = "noob";
            noob += " gamer";
            System.out.println(noob);
            System.out.println(returnList);
            return returnList;
        });
    }

    private void getUser() {
        get("/api/users/:id", (req, res) -> {
            res.type("application/json");
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
                // Get Subteams for User
                String subteamSql = "select subteam.subteam from subteam where subteam.student_id='" + user.getId() + "'";
                ResultSet rs2 = db.createStatement().executeQuery(subteamSql);
                while (rs2.next()) {
                    user.subteams.add(rs2.getString("subteam"));
                }
                // Get Permissions for User
                String permSql = "select perm from permission where permission.student_id='" + user.getId() + "'";
                ResultSet rs3 = db.createStatement().executeQuery(permSql);
                while (rs3.next()) {
                    user.perms.add(rs3.getString("perm"));
                }
            }
            rs.close();
            System.out.println(user);
            return user.toString();
        });
    }

    private void createUser() {
        post("/api/users", (req, res) -> {
            res.type("application/json");
            User user = gson.fromJson(req.body(), User.class);
            System.out.println("GIVEN STUDENT: " + user);
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
                    "'" + user.getShirtSize() + "'" +
                    "'" + user.getJacketSize() + "'" +
                    "'" + user.getDiscordID() + "'" +
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
            return user.toString();
        });
    }

}
