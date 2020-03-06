package com.bk1031.wbdatabase.model;

import java.util.ArrayList;

public class MatchData {

    private String matchID;
    private String teamID;
    private String scouterID;
    private String alliance;
    private int preload;
    private boolean park;
    private boolean level;

    Auto auto;
    Spin spin;
    public ArrayList<PowerCell> powercells = new ArrayList<>();
    public ArrayList<Climb> climbs = new ArrayList<>();
    public ArrayList<Disconnect> disconnects = new ArrayList<>();

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getScouterID() {
        return scouterID;
    }

    public void setScouterID(String scouterID) {
        this.scouterID = scouterID;
    }

    public String getAlliance() {
        return alliance;
    }

    public void setAlliance(String alliance) {
        this.alliance = alliance;
    }

    public int getPreload() {
        return preload;
    }

    public void setPreload(int preload) {
        this.preload = preload;
    }

    public boolean isPark() {
        return park;
    }

    public void setPark(boolean park) {
        this.park = park;
    }

    public boolean isLevel() {
        return level;
    }

    public void setLevel(boolean level) {
        this.level = level;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Spin getSpin() {
        return spin;
    }

    public void setSpin(Spin spin) {
        this.spin = spin;
    }

    @Override
    public String toString() {
        return "{" +
                "\"matchID\":\"" + matchID + "\"," +
                "\"teamID\":\"" + teamID + "\"," +
                "\"scouterID\":\"" + scouterID + "\"," +
                "\"alliance\":\"" + alliance + "\"," +
                "\"preload\":" + preload + "," +
                "\"level\":" + level + "," +
                "\"auto\":" + auto.toString() + "," +
                "\"spin\":" + spin.toString() + "," +
                "\"powercells\":" + powercells.toString() + "," +
                "\"climbs\":" + climbs.toString() + "," +
                "\"disconnects\":" + disconnects.toString() + "," +
                "\"park\":" + park + "" +
                "}";
    }
}
