package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Post;
import com.bk1031.wbdatabase.model.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import static spark.Spark.*;

public class PostController {

    private Connection db;

    Gson gson = new Gson();

    public PostController(Connection db) {
        this.db = db;
        getAllPosts();
    }

    private void getAllPosts() {
        get("/api/posts", (request, response) -> {
            ArrayList<Post> returnList = new ArrayList<>();
            String sql = "SELECT * FROM \"post\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while(rs.next()) {
                Post post = new Post();
                post.setId(rs.getString("id"));
                post.setBody(rs.getString("body"));
                post.setAuthorID(rs.getString("author_id"));
                post.setDate(rs.getTimestamp("date").toString());
                post.setTitle(rs.getString("title"));
                // Get Tags for Post
                String tagSql = "select post_tag.tag from post_tag where post_tag.post_id='" + post.getId() + "'";
                ResultSet rs2 = db.createStatement().executeQuery(tagSql);
                while (rs2.next()) {
                    post.tags.add(rs2.getString("tag"));
                }
                returnList.add(post);
            }
            rs.close();
            return returnList;
        });
    }
}
