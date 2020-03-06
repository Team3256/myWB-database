package com.bk1031.wbdatabase.model;

/**
 * User: bharat
 * Date: 3/6/20
 * Time: 10:57 AM
 */
public class Disconnect {

    private String matchID;
    private String teamID;
    private double startTime;
    private double duration;

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

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "{" +
                "\"matchID\":\"" + matchID + "\"," +
                "\"teamID\":\"" + teamID + "\"," +
                "\"startTime\":" + startTime + "," +
                "\"duration\":" + duration + "" +
                "}";
    }

}
