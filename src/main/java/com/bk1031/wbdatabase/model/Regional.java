package com.bk1031.wbdatabase.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: bharat
 * Date: 3/4/20
 * Time: 8:46 AM
 */
public class Regional {

    private String id;
    private String city;
    private String country;
    private Date startDate;
    private Date endDate;
    private int year;
    private String shortName;
    private String name;
    private String eventCode;

    public List<Team> teams = new ArrayList<Team>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"city\":\"" + city + "\"," +
                "\"country\":\"" + country + "\"," +
                "\"startDate\":\"" + startDate + "\"," +
                "\"endDate\":\"" + endDate + "\"," +
                "\"year\":" + year + "," +
                "\"shortName\":\"" + shortName + "\"," +
                "\"name\":\"" + name + "\"," +
                "\"eventCode\":\"" + eventCode + "\"," +
                "\"teams\":" + teams.toString() +
                "}";
    }

}
