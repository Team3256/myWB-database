package com.bk1031.wbdatabase.model;

public class ExcusedAttendance {

    private String userID;
    private String eventID;
    private String status;
    private String reason;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "{" +
                "\"userID\":\"" + userID + "\"," +
                "\"eventID\":\"" + eventID + "\"," +
                "\"status\":\"" + status + "\"," +
                "\"reason\":\"" + reason + "\"" +
                "}";
    }
}
