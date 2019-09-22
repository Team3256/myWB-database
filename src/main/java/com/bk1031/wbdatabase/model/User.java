package com.bk1031.wbdatabase.model;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<String> subteamList = new ArrayList<>();
    public List<String> permList = new ArrayList<>();

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
                "\"subteams\":" + subteamList.toString() + "," +
                "\"varsity\":" + varsity + "," +
                "\"shirtSize\":\"" + shirtSize + "\"," +
                "\"jacketSize\":\"" + jacketSize + "\"," +
                "\"discordID\":" + discordID + "," +
                "\"perms\":" + permList.toString() + "," +
                "}";
    }
}
