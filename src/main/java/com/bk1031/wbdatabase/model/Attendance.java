package com.bk1031.wbdatabase.model;

import java.sql.Timestamp;

public class Attendance {

    private String userID;
    private String eventID;
    private Timestamp checkIn;
    private Timestamp checkOut;
    private double hours;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public Timestamp getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Timestamp checkIn) {
        this.checkIn = checkIn;
    }

    public Timestamp getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Timestamp checkOut) {
        this.checkOut = checkOut;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "{" +
                "\"user_id\":\"" + userID + "\"," +
                "\"event_id\":\"" + eventID + "\"," +
                "\"check_in\":\"" + checkIn + "\"," +
                "\"check_out\":\"" + checkOut + "\"," +
                "\"hours\":" + hours +
                "}";
    }
}
