package com.bk1031.wbdatabase.model;

import java.util.Date;

/**
 * User: bharat
 * Date: 1/2/21
 * Time: 8:22 PM
 */
public class Token {

    private String id;
    private int permission;
    private Date created;

    public Token(String id, int permission, Date created) {
        this.id = id;
        this.permission = permission;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "{" +
                "\"token\":\"" + id + "\"," +
                "\"permission\":\"" + permission + "\"," +
                "\"created\":\"" + created.toString() + "\"" +
                "}";
    }
}
