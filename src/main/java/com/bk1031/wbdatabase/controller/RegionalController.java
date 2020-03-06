package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Regional;
import com.bk1031.wbdatabase.model.Team;
import com.bk1031.wbdatabase.model.User;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * User: bharat
 * Date: 3/4/20
 * Time: 3:38 PM
 */
public class RegionalController {

    private Connection db;

    Gson gson = new Gson();

    public RegionalController(Connection db) {
        this.db = db;
        getAllRegionals();
        getAllTeamsForRegional();
        createNewRegional();
    }

    private void getAllRegionals() {
        get("/api/scouting/regionals", (req, res) -> {
            List<Regional> returnList = new ArrayList<>();
            // Get Regionals
            String sql = "SELECT * FROM \"regional\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                Regional regional = new Regional();
                regional.setId(rs.getString("id"));
                regional.setCity(rs.getString("city"));
                regional.setCountry(rs.getString("country"));
                regional.setStartDate(rs.getDate("start_date"));
                regional.setEndDate(rs.getDate("end_date"));
                regional.setYear(rs.getInt("year"));
                regional.setShortName(rs.getString("short_name"));
                regional.setName(rs.getString("name"));
                regional.setEventCode(rs.getString("event_code"));
                // Get Teams for Regionals
                String teamSql = "select team_id from regional_team where regional_id='" + regional.getId() + "'";
                ResultSet rs2 = db.createStatement().executeQuery(teamSql);
                while (rs2.next()) {
                    String teamSql2 = "select * from team where id='" + rs2.getString("team_id") + "'";
                    ResultSet rs3 = db.createStatement().executeQuery(teamSql2);
                    while (rs3.next()) {
                        Team team = new Team();
                        team.setId(rs3.getString("id"));
                        team.setName(rs3.getString("name"));
                        team.setNickname(rs3.getString("nickname"));
                        regional.teams.add(team);
                    }
                }
                returnList.add(regional);
            }
            rs.close();
            res.type("application/json");
            res.body(returnList.toString());
            return res;
        });
    }

    private void getAllTeamsForRegional() {
        get("/api/scouting/regionals/:id/teams", (req, res) -> {
            List<Team> returnList = new ArrayList<>();
            // Get Regionals
            String sql = "SELECT team_id FROM \"regional_team\" WHERE regional_id='" + req.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                String teamSql = "select * from team where id='" + rs.getString("team_id") + "'";
                ResultSet rs2 = db.createStatement().executeQuery(teamSql);
                while (rs2.next()) {
                    Team team = new Team();
                    team.setId(rs.getString("id"));
                    team.setName(rs.getString("name"));
                    team.setNickname(rs.getString("nickname"));
                    returnList.add(team);
                }
            }
            rs.close();
            res.type("application/json");
            res.body(returnList.toString());
            return res;
        });
    }

    private void createNewRegional() {
        post("/api/scouting/regionals", (req, res) -> {
            Regional regional = gson.fromJson(req.body(), Regional.class);
            System.out.println("PARSED REGIONAL: " + regional);
            if (regional.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            String existsSql = "SELECT COUNT(1) FROM \"regional\" WHERE id = '" + regional.getId() + "'";
            ResultSet rs = db.createStatement().executeQuery(existsSql);
            while (rs.next()) {
                if (rs.getInt("count") == 1) {
                    res.status(409);
                    res.type("application/json");
                    res.body("{\"message\": \"Regional already exists with id: " + regional.getId() + "\"}");
                    return res;
                }
            }
            String sql = "INSERT INTO \"regional\" VALUES " +
                    "(" +
                    "'" + regional.getId() + "'," +
                    "'" + regional.getCity() + "'," +
                    "'" + regional.getCountry() + "'," +
                    "'" + regional.getStartDate() + "'," +
                    "'" + regional.getEndDate() + "'," +
                    regional.getYear() + "," +
                    "'" + regional.getShortName() + "'," +
                    "'" + regional.getName() + "'," +
                    "'" + regional.getEventCode() + "'" +
                    ")";
            db.createStatement().executeUpdate(sql);
            for (Team team : regional.teams) {
                existsSql = "SELECT COUNT(1) FROM \"team\" WHERE id = '" + team.getId() + "'";
                ResultSet rs2 = db.createStatement().executeQuery(existsSql);
                while (rs2.next()) {
                    if (rs2.getInt("count") == 0) {
                        sql = "INSERT INTO team VALUES " +
                                "(" +
                                "'" + team.getId() + "'," +
                                "'" + team.getNickname().replace("'", "''") + "'," +
                                "'" + team.getName().replace("'", "''") + "'" +
                                ")";
                        db.createStatement().executeUpdate(sql);
                    }
                }
                sql = "INSERT INTO regional_team VALUES " +
                        "(" +
                        "'" + regional.getId() + "'," +
                        "'" + team.getId() + "'" +
                        ")";
                db.createStatement().executeUpdate(sql);
            }
            db.commit();
            System.out.println("Inserted records into the table...");
            res.type("application/json");
            res.body(regional.toString());
            return res;
        });
    }

}
