package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Post;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import static spark.Spark.get;

public class PostController {
    private String id;
    private String title;
    private Timestamp date;
    private String body;

    private Connection db;

    Gson gson = new Gson();

    public PostController(Connection db) {
        this.db = db;
        getAllPosts();
        getPost();
    }

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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private void getAllPosts(){
        get("/api/posts", (request, response) -> {
            ArrayList<Post> returnList = new ArrayList<>();
            String sql = "SELECT * FROM \"posts\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while(rs.next()){
                Post post = new Post();
                post.setId(rs.getString("id"));
                post.setBody(rs.getString("body"));
                post.setDate(rs.getTimestamp("date"));
                post.setTitle(rs.getString("title"));
                returnList.add(post);
            }
            rs.close();
            return returnList;
        });
    }

    private void getPost(){
        get("/api/posts", (request, response) -> {
            // Get post
            Post post = new Post();
            String sql = "SELECT * FROM \"posts\" WHERE id='" + request.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                post.setId(rs.getString("id"));
                post.setBody(rs.getString("body"));
                post.setDate(rs.getTimestamp("date"));
                post.setTitle(rs.getString("title"));
            }
            rs.close();
            if (post.toString().contains("null")){
                response.status(404);
                response.type("application/json");
                response.body("{\"message\": \"Requested post not found\"}");
                return response;
            }
            response.type("application/json");
            response.body(post.toString());
            return response;
        });
    }

    @Override
    public String toString(){
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"title\":\"" + title + "\"," +
                "\"date\":\"" + date + "\"," +
                "\"body\":\"" + body + "\"" +
                "}";
    }
}
