package com.bk1031.wbdatabase.model;

public class Auto {

    private String matchID;
    private String teamID;
    private String startPosition;
    private boolean crossed;
    private double crossTime;
    private boolean trench;

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

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public boolean isCrossed() {
        return crossed;
    }

    public void setCrossed(boolean crossed) {
        this.crossed = crossed;
    }

    public double getCrossTime() {
        return crossTime;
    }

    public void setCrossTime(double crossTime) {
        this.crossTime = crossTime;
    }

    public boolean isTrench() {
        return trench;
    }

    public void setTrench(boolean trench) {
        this.trench = trench;
    }

    @Override
    public String toString() {
        return "{" +
                "\"matchID\":\"" + matchID + "\"," +
                "\"teamID\":\"" + teamID + "\"," +
                "\"startPosition\":\"" + startPosition + "\"," +
                "\"crossed\":" + crossed + "," +
                "\"crossTime\":" + crossTime + "," +
                "\"trench\":" + trench + "" +
                "}";
    }

}
