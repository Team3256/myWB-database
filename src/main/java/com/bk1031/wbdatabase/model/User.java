package com.bk1031.wbdatabase.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int grade;
    private String role;
    private boolean varsity;
    private String shirtSize;
    private String jacketSize;
    private String discordID;
    private String discordAuthToken;

    public List<String> subteams = new ArrayList<>();
    public List<String> perms = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isVarsity() {
        return varsity;
    }

    public void setVarsity(boolean varsity) {
        this.varsity = varsity;
    }

    public String getShirtSize() {
        return shirtSize;
    }

    public void setShirtSize(String shirtSize) {
        this.shirtSize = shirtSize;
    }

    public String getJacketSize() {
        return jacketSize;
    }

    public void setJacketSize(String jacketSize) {
        this.jacketSize = jacketSize;
    }

    public String getDiscordID() {
        return discordID;
    }

    public void setDiscordID(String discordID) {
        this.discordID = discordID;
    }

    public String getDiscordAuthToken() {
        return discordAuthToken;
    }

    public void setDiscordAuthToken(String discordAuthToken) {
        this.discordAuthToken = discordAuthToken;
    }

    public String getPerms() {
        String returnString = "[";
        for (String perm: perms) {
            returnString += "\"" + perm + "\"";
            if (perms.indexOf(perm) < perms.size() - 1) {
                returnString += ",";
            }
        }
        returnString += "]";
        return returnString;
    }

    public String getSubteams() {
        String returnString = "[";
        for (String subteam: subteams) {
            returnString += "\"" + subteam + "\"";
            if (subteams.indexOf(subteam) < subteams.size() - 1) {
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
                "\"firstName\":\"" + firstName + "\"," +
                "\"lastName\":\"" + lastName + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"phone\":\"" + phone + "\"," +
                "\"grade\":" + grade + "," +
                "\"role\":\"" + role + "\"," +
                "\"varsity\":" + varsity + "," +
                "\"shirtSize\":\"" + shirtSize + "\"," +
                "\"jacketSize\":\"" + jacketSize + "\"," +
                "\"discordID\":\"" + discordID + "\"," +
                "\"discordAuthToken\":\"" + discordAuthToken + "\"," +
                "\"perms\":" + getPerms() + "," +
                "\"subteams\":" + getSubteams() +
                "}";
    }
}
