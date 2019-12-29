package com.bk1031.wbdatabase.model;


public class Attendance {

    private String userID;
    private String eventID;
    private String checkIn;
    private String checkOut;
    private String status;
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

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                "\"userID\":\"" + userID + "\"," +
                "\"eventID\":\"" + eventID + "\"," +
                "\"checkIn\":\"" + checkIn + "\"," +
                "\"checkOut\":\"" + checkOut + "\"," +
                "\"status\":\"" + status + "\"," +
                "\"hours\":" + hours +
                "}";
    }
}
