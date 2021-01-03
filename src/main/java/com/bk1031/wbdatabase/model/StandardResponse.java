package com.bk1031.wbdatabase.model;

import java.util.Date;

/**
 * User: bharat
 * Date: 1/3/21
 * Time: 12:14 AM
 */
public class StandardResponse {

    private String status;
    private Date date;
    private String message;
    private String data;

    public StandardResponse(String status, Date date, String message, String data) {
        this.status = status;
        this.date = date;
        this.message = message;
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "\"status\":\"" + status + "\"," +
                "\"date\":\"" + date.toString() + "\"," +
                "\"message\":\"" + message + "\"," +
                "\"data\":" + data +
                "}";
    }
}