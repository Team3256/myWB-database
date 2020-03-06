package com.bk1031.wbdatabase.model;

public class PowerCell {

    private String matchID;
    private String teamID;
    private String dropLocation;
    private double pickupTime;
    private double cycleTime;

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

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public double getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(double pickupTime) {
        this.pickupTime = pickupTime;
    }

    public double getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(double cycleTime) {
        this.cycleTime = cycleTime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"matchID\":\"" + matchID + "\"," +
                "\"teamID\":\"" + teamID + "\"," +
                "\"dropLocation\":\"" + dropLocation + "\"," +
                "\"pickupTime\":" + pickupTime + "," +
                "\"cycleTime\":" + cycleTime + "" +
                "}";
    }

}
