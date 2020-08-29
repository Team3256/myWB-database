package com.bk1031.wbdatabase.model;

public class Spin {

    private String matchID;
    private String teamID;
    private double rotationTime;
    private boolean rotation;
    private double positionTime;
    private boolean position;

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

    public double getRotationTime() {
        return rotationTime;
    }

    public void setRotationTime(double rotationTime) {
        this.rotationTime = rotationTime;
    }

    public boolean isRotation() {
        return rotation;
    }

    public void setRotation(boolean rotation) {
        this.rotation = rotation;
    }

    public double getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(double positionTime) {
        this.positionTime = positionTime;
    }

    public boolean isPosition() {
        return position;
    }

    public void setPosition(boolean position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "{" +
                "\"matchID\":\"" + matchID + "\"," +
                "\"teamID\":\"" + teamID + "\"," +
                "\"rotationTime\":" + rotationTime + "," +
                "\"rotation\":" + rotation + "," +
                "\"positionTime\":" + positionTime + "," +
                "\"position\":" + position +
                "}";
    }

}
