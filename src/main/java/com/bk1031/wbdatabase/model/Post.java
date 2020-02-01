package com.bk1031.wbdatabase.model;

import java.util.ArrayList;
import java.util.List;

public class Post {

    private String id;
    private String title;
    private String date;
    private String body;
    private String authorID;

    public List<String> tags = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        String returnString = "[";
        for (String tag: tags) {
            returnString += "\"" + tag + "\"";
            if (tags.indexOf(tag) < tags.size() - 1) {
                returnString += ",";
            }
        }
        returnString += "]";
        return returnString;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"title\":\"" + title + "\"," +
                "\"author_id\":\"" + authorID + "\"," +
                "\"tags\":" + getTags() + "," +
                "\"date\":\"" + date + "\"," +
                "\"body\":\"" + body + "\"" +
                "}";
    }
}