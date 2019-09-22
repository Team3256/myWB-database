package com.bk1031.wbdatabase.model;

public class Student {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int grade;
    private String role;
    private boolean varsity;
    private boolean safetyGlasses;
    private String shirtSize;

    public Student(String id, String firstName, String lastName, String email, String phone, int grade, String role, boolean varsity, boolean safetyGlasses, String shirtSize) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
            this.grade = grade;
            this.role = role;
            this.varsity = varsity;
            this.safetyGlasses = safetyGlasses;
            this.shirtSize = shirtSize;
    }

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

    public boolean isSafetyGlasses() {
        return safetyGlasses;
    }

    public void setSafetyGlasses(boolean safetyGlasses) {
        this.safetyGlasses = safetyGlasses;
    }

    public String getShirtSize() {
        return shirtSize;
    }

    public void setShirtSize(String shirtSize) {
        this.shirtSize = shirtSize;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"firstName\":\"" + firstName + "\"," +
                "\"lastName\":\"" + lastName + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"phone\":\"" + phone + "\"," +
                "\"role\":\"" + role + "\"," +
                "\"varsity\":" + varsity + "," +
                "\"safetyGlasses\":" + safetyGlasses + "," +
                "\"shirtSize\":\"" + shirtSize + "\"" +
                "}";
    }
}
