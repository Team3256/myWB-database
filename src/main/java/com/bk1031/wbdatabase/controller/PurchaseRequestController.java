package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Event;
import com.bk1031.wbdatabase.model.PurchaseRequest;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;

import static spark.Spark.post;

public class PurchaseRequestController {

    private Connection db;

    Gson gson = new Gson();

    public PurchaseRequestController(Connection db) {
        this.db = db;
        createPurchaseRequest();
    }

    private void createPurchaseRequest() {
        post("/api/purchase-requests", (req, res) -> {
            PurchaseRequest pr = gson.fromJson(req.body(), PurchaseRequest.class);
            System.out.println("PARSED PURCHASE REQUEST: " + pr);
            if (pr.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            String existsSql = "SELECT COUNT(1) FROM \"purchase_request\" WHERE id = '" + pr.getId() + "'";
            ResultSet rs = db.createStatement().executeQuery(existsSql);
            while (rs.next()) {
                if (rs.getInt("count") == 1) {
                    res.status(409);
                    res.type("application/json");
                    res.body("{\"message\": \"Purchase request already exists with id: " + pr.getId() + "\"}");
                    return res;
                }
            }
            String sql = "INSERT INTO \"purchase_request\" VALUES " +
                    "(" +
                    "'" + pr.getId() + "'," +
                    "" + pr.isSheet() + "," +
                    "'" + pr.getUserID() + "'," +
                    "'" + pr.getPartName() + "'," +
                    "" + pr.getPartQuantity() + "," +
                    "'" + pr.getPartUrl() + "'," +
                    "'" + pr.getVendor() + "'," +
                    "'" + pr.getNeedBy() + "'," +
                    "'" + pr.getPartNumber() + "'," +
                    "" + pr.getCost() + "," +
                    "" + pr.getTotalCost() + "," +
                    "'" + pr.getJustification() + "'" +
                    ")";
            db.createStatement().executeUpdate(sql);
            db.commit();
            System.out.println("Inserted records into the table...");
            res.type("application/json");
            res.body(pr.toString());
            return res;
        });
    }

}
