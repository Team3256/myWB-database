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
    private String gender;
    private String shirtSize;
    private String jacketSize;
    private String discordID;
    private String discordAuthToken;

    public List<String> roles = new ArrayList<>();

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getRoles() {
        String returnString = "[";
        for (String role: roles) {
            returnString += "\"" + role + "\"";
            if (roles.indexOf(role) < roles.size() - 1) {
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
                "\"gender\":\"" + gender + "\"," +
                "\"shirtSize\":\"" + shirtSize + "\"," +
                "\"jacketSize\":\"" + jacketSize + "\"," +
                "\"discordID\":\"" + discordID + "\"," +
                "\"discordAuthToken\":\"" + discordAuthToken + "\"," +
                "\"roles\":" + roles +
                "}";
    }
}
