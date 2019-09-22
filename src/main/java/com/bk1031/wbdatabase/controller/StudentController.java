package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Student;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import static spark.Spark.*;

public class StudentController {

    private Connection db;

    Gson gson = new Gson();

    public StudentController(Connection db) {
        this.db = db;
        getAllStudents();
        getStudent();
        createStudent();
    }

    private void getAllStudents() {
        get("/api/hr/users", (req, res) -> {
            res.type("application/json");
            String returnString = "[";
            String sql = "SELECT * FROM STUDENT";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while(rs.next()) {
                Student student = new Student(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("grade"),
                        rs.getString("role"),
                        rs.getBoolean("varsity"),
                        rs.getBoolean("safety_glasses"),
                        rs.getString("shirt_size")
                );
                System.out.println(student);
                returnString += student.toString();
                if (!rs.isLast()) {
                    returnString += ",";
                }
            }
            rs.close();
            returnString += "]";
            return returnString;
        });
    }

    private void getStudent() {
        get("/api/hr/users/:id", (req, res) -> {
            res.type("application/json");
            String returnString = "";
            String sql = "SELECT * FROM STUDENT WHERE id='" + req.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while(rs.next()) {
                Student student = new Student(
                        rs.getString("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("grade"),
                        rs.getString("role"),
                        rs.getBoolean("varsity"),
                        rs.getBoolean("safety_glasses"),
                        rs.getString("shirt_size")
                );
                System.out.println(student);
                returnString += student.toString();
            }
            rs.close();
            return returnString;
        });
    }

    private void createStudent() {
        post("/api/hr/users", (req, res) -> {
            res.type("application/json");
            Student student = gson.fromJson(req.body(), Student.class);
            System.out.println("GIVEN STUDENT: " + student);
            String sql = "INSERT INTO STUDENT VALUES " +
                    "(" +
                    "'" + student.getId() + "'," +
                    "'" + student.getFirstName() + "'," +
                    "'" + student.getLastName() + "'," +
                    "'" + student.getEmail() + "'," +
                    "'" + student.getPhone() + "'," +
                    "'" + student.getGrade() + "'," +
                    "'" + student.getRole() + "'," +
                    student.isVarsity() + "," +
                    student.isSafetyGlasses() + "," +
                    "'" + student.getShirtSize() + "'" +
                    ")";
            db.createStatement().executeUpdate(sql);
            System.out.println("Inserted records into the table...");
            return student.toString();
        });
    }

}
