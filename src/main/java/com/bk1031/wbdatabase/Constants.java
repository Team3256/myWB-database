package com.bk1031.wbdatabase;

import java.util.ArrayList;

public class Constants {
    final static String URL = "jdbc:postgresql://localhost:5432/wb-database";
    final static String USER = "postgres";
    final static String PASSWORD = "password";
    final static String initPath = "/src/main/resources/init.sql";
    static ArrayList<String> apiKeys = new ArrayList<>();
}
