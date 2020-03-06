package com.bk1031.wbdatabase.model;

/**
 * User: bharat
 * Date: 3/6/20
 * Time: 10:55 AM
 */
public class Climb {

    private String matchID;
    private String teamID;
    private double startTime;
    private double climbTime;
    private boolean dropped;

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

    public double getClimbTime() {
        return climbTime;
    }

    public void setClimbTime(double climbTime) {
        this.climbTime = climbTime;
    }

    public boolean isDropped() {
        return dropped;
    }

    public void setDropped(boolean dropped) {
        this.dropped = dropped;
    }

    @Override
    public String toString() {
        return "{" +
                "\"matchID\":\"" + matchID + "\"," +
                "\"teamID\":\"" + teamID + "\"," +
                "\"startTime\":" + startTime + "," +
                "\"climbTime\":" + climbTime + "," +
                "\"dropped\":" + dropped + "" +
                "}";
    }

}
