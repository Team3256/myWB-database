package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Match;
import com.bk1031.wbdatabase.model.Regional;
import com.google.gson.Gson;
import static spark.Spark.*;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * User: bharat
 * Date: 3/6/20
 * Time: 11:16 AM
 */
public class MatchController {

    private Connection db;

    Gson gson = new Gson();

    public MatchController(Connection db) {
        this.db = db;
        createMatch();
    }

    private void createMatch() {
        post("/api/scouting/matches", (req, res) -> {
            Match match = gson.fromJson(req.body(), Match.class);
            System.out.println("PARSED MATCH: " + match);
            if (match.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            // Check if match data exists
            String checkSql = "SELECT COUNT(*) FROM \"match_team\" WHERE match_id='" + match.getId() + "' AND team_id='" + match.getMatchData().getTeamID() + "'";
            ResultSet rs = db.createStatement().executeQuery(checkSql);
            while (rs.next()) {
                if (rs.getInt("count") == 1) {
                    res.status(409);
                    res.type("application/json");
                    res.body("{\"message\": \"Match data already exists with id: " + match.getId() + " and team: " + match.getMatchData().getTeamID() + "\"}");
                    return res;
                }
            }
            String sql = "INSERT INTO \"match\" VALUES " +
                    "(" +
                    "'" + match.getId() + "'," +
                    "'" + match.getRegionalID() + "'," +
                    "" + match.getMatchNum() +
                    ")";
            db.createStatement().executeUpdate(sql);
            sql = "INSERT INTO \"match_team\" VALUES " +
                    "(" +
                    "'" + match.getId() + "'," +
                    "'" + match.getMatchData().getTeamID() + "'," +
                    "'" + match.getMatchData().getScouterID() + "'," +
                    "'" + match.getMatchData().getAlliance() + "'," +
                    "" + match.getMatchData().getPreload() + "," +
                    "" + match.getMatchData().isLevel() + "," +
                    "" + match.getMatchData().isPark() +
                    ")";
            db.createStatement().executeUpdate(sql);
            sql = "INSERT INTO \"auto\" VALUES " +
                    "(" +
                    "'" + match.getId() + "'," +
                    "'" + match.getMatchData().getTeamID() + "'," +
                    "'" + match.getMatchData().getAuto().getStartPosition() + "'," +
                    "" + match.getMatchData().getAuto().isCrossed() + "," +
                    "" + match.getMatchData().getAuto().getCrossTime() + "," +
                    "" + match.getMatchData().getAuto().isTrench() +
                    ")";
            db.createStatement().executeUpdate(sql);
            sql = "INSERT INTO \"spin\" VALUES " +
                    "(" +
                    "'" + match.getId() + "'," +
                    "'" + match.getMatchData().getTeamID() + "'," +
                    "" + match.getMatchData().getSpin().isRotation() + "," +
                    "" + match.getMatchData().getSpin().getRotationTime() + "," +
                    "" + match.getMatchData().getSpin().isPosition() + "," +
                    "" + match.getMatchData().getSpin().getPositionTime() +
                    ")";
            db.createStatement().executeUpdate(sql);
            sql = "INSERT INTO \"spin\" VALUES " +
                    "(" +
                    "'" + match.getId() + "'," +
                    "'" + match.getMatchData().getTeamID() + "'," +
                    "" + match.getMatchData().getSpin().isRotation() + "," +
                    "" + match.getMatchData().getSpin().getRotationTime() + "," +
                    "" + match.getMatchData().getSpin().isPosition() + "," +
                    "" + match.getMatchData().getSpin().getPositionTime() +
                    ")";
            db.createStatement().executeUpdate(sql);
            for (int i = 0; i < match.getMatchData().powercells.size(); i++) {
                sql = "INSERT INTO \"power_cell\" VALUES " +
                        "(" +
                        "'" + match.getId() + "'," +
                        "'" + match.getMatchData().getTeamID() + "'," +
                        "'" + match.getMatchData().powercells.get(i).getDropLocation() + "'," +
                        "" + match.getMatchData().powercells.get(i).getPickupTime() + "," +
                        "" + match.getMatchData().powercells.get(i).getCycleTime() +
                        ")";
                db.createStatement().executeUpdate(sql);
            }
            for (int i = 0; i < match.getMatchData().climbs.size(); i++) {
                sql = "INSERT INTO \"climb\" VALUES " +
                        "(" +
                        "'" + match.getId() + "'," +
                        "'" + match.getMatchData().getTeamID() + "'," +
                        "" + match.getMatchData().climbs.get(i).getStartTime() + "," +
                        "" + match.getMatchData().climbs.get(i).getClimbTime() + "," +
                        "" + match.getMatchData().climbs.get(i).isDropped() +
                        ")";
                db.createStatement().executeUpdate(sql);
            }
            for (int i = 0; i < match.getMatchData().disconnects.size(); i++) {
                sql = "INSERT INTO \"disconnect\" VALUES " +
                        "(" +
                        "'" + match.getId() + "'," +
                        "'" + match.getMatchData().getTeamID() + "'," +
                        "" + match.getMatchData().disconnects.get(i).getStartTime() + "," +
                        "" + match.getMatchData().disconnects.get(i).getDuration() +
                        ")";
                db.createStatement().executeUpdate(sql);
            }
            db.commit();
            System.out.println("Inserted records into the table...");
            res.type("application/json");
            res.body(match.toString());
            return res;
        });
    }
}
