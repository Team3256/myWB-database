package com.bk1031.wbdatabase.model;

/**
 * User: bharat
 * Date: 3/4/20
 * Time: 8:55 AM
 */
public class Team {

    private String id;
    private String nickname;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"nickname\":\"" + nickname + "\"," +
                "\"name\":\"" + name + "\"" +
                "}";
    }
}
