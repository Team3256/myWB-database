package com.bk1031.wbdatabase.model;

import java.util.ArrayList;

public class Match {

    private String id;
    private String regionalID;
    private int matchNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegionalID() {
        return regionalID;
    }

    public void setRegionalID(String regionalID) {
        this.regionalID = regionalID;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public MatchData getMatchData() {
        return matchData;
    }

    public void setMatchData(MatchData matchData) {
        this.matchData = matchData;
    }

    MatchData matchData;

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"regionalID\":\"" + regionalID + "\"," +
                "\"matchNum\":\"" + matchNum + "\"," +
                "\"matchData\":" + matchData.toString() + "" +
                "}";
    }
}
