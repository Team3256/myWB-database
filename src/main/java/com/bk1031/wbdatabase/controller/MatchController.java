package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.*;
import com.google.gson.Gson;
import static spark.Spark.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

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
        getMatchesFromRegional();
        getMatchesFromTeam();
    }

    private void getMatchesFromRegional() {
        get("/api/scouting/regionals/:id/matches", (req, res) -> {
            ArrayList<Match> returnList = new ArrayList<>();
            res.type("application/json");
            res.body(returnList.toString());
            return res;
        });
    }

    private void getMatchesFromTeam() {
        get("/api/scouting/teams/:id/matches", (req, res) -> {
            ArrayList<MatchData> returnList = new ArrayList<>();
            String sql = "SELECT * FROM \"match_team\" WHERE team_id='" + req.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                MatchData matchData = new MatchData();
                matchData.setMatchID(rs.getString("match_id"));
                matchData.setTeamID(rs.getString("team_id"));
                matchData.setScouterID(rs.getString("scouter_id"));
                matchData.setAlliance(rs.getString("alliance"));
                matchData.setPreload(rs.getInt("preload"));
                matchData.setLevel(rs.getBoolean("level"));
                matchData.setPark(rs.getBoolean("park"));

                matchData.setAuto(new Auto());
                matchData.setSpin(new Spin());

                sql = "SELECT * FROM \"auto\" WHERE match_id='" + matchData.getMatchID() + "' AND team_id='" + matchData.getTeamID() + "'";
                ResultSet rs2 = db.createStatement().executeQuery(sql);
                while (rs2.next()) {
                    Auto auto = new Auto();
                    auto.setMatchID(matchData.getMatchID());
                    auto.setTeamID(matchData.getTeamID());
                    auto.setStartPosition(rs2.getString("start_position"));
                    auto.setCrossed(rs2.getBoolean("crossed"));
                    auto.setCrossTime(rs2.getDouble("cross_time"));
                    auto.setTrench(rs2.getBoolean("trench"));
                    matchData.setAuto(auto);
                }

                sql = "SELECT * FROM \"spin\" WHERE match_id='" + matchData.getMatchID() + "' AND team_id='" + matchData.getTeamID() + "'";
                ResultSet rs3 = db.createStatement().executeQuery(sql);
                while (rs3.next()) {
                    Spin spin = new Spin();
                    spin.setMatchID(matchData.getMatchID());
                    spin.setTeamID(matchData.getTeamID());
                    spin.setRotationTime(rs3.getDouble("rotation_time"));
                    spin.setRotation(rs3.getBoolean("rotation"));
                    spin.setPositionTime(rs3.getDouble("position_time"));
                    spin.setPosition(rs3.getBoolean("position"));
                    matchData.setSpin(spin);
                }

                sql = "SELECT * FROM \"power_cell\" WHERE match_id='" + matchData.getMatchID() + "' AND team_id='" + matchData.getTeamID() + "'";
                ResultSet rs4 = db.createStatement().executeQuery(sql);
                while (rs4.next()) {
                    PowerCell power = new PowerCell();
                    power.setMatchID(matchData.getMatchID());
                    power.setTeamID(matchData.getTeamID());
                    power.setDropLocation(rs4.getString("drop_location"));
                    power.setPickupTime(rs4.getDouble("pickup_time"));
                    power.setCycleTime(rs4.getDouble("cycle_time"));
                    matchData.powercells.add(power);
                }

                sql = "SELECT * FROM \"climb\" WHERE match_id='" + matchData.getMatchID() + "' AND team_id='" + matchData.getTeamID() + "'";
                ResultSet rs5 = db.createStatement().executeQuery(sql);
                while (rs5.next()) {
                    Climb climb = new Climb();
                    climb.setMatchID(matchData.getMatchID());
                    climb.setTeamID(matchData.getTeamID());
                    climb.setStartTime(rs5.getDouble("start_time"));
                    climb.setClimbTime(rs5.getDouble("climb_time"));
                    climb.setDropped(rs5.getBoolean("dropped"));
                    matchData.climbs.add(climb);
                }

                sql = "SELECT * FROM \"disconnect\" WHERE match_id='" + matchData.getMatchID() + "' AND team_id='" + matchData.getTeamID() + "'";
                ResultSet rs6 = db.createStatement().executeQuery(sql);
                while (rs6.next()) {
                    Disconnect dc = new Disconnect();
                    dc.setMatchID(matchData.getMatchID());
                    dc.setTeamID(matchData.getTeamID());
                    dc.setStartTime(rs6.getDouble("start_time"));
                    dc.setDuration(rs6.getDouble("duration"));
                    matchData.disconnects.add(dc);
                }
                System.out.println(matchData);
                returnList.add(matchData);
            }
            res.type("application/json");
            res.body(returnList.toString());
            return res;
        });
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
