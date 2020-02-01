package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Event;
import com.bk1031.wbdatabase.model.PurchaseRequest;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class PurchaseRequestController {

    private Connection db;

    Gson gson = new Gson();

    public PurchaseRequestController(Connection db) {
        this.db = db;
        getAllPurchaseRequests();
        createPurchaseRequest();
    }

    private void getAllPurchaseRequests() {
        get("/api/purchase-requests", (request, response) -> {
            List<PurchaseRequest> returnList = new ArrayList<>();
            // Get Events
            String sql = "SELECT * FROM \"purchase_request\"";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                PurchaseRequest pr = new PurchaseRequest();
                pr.setId(rs.getString("id"));
                pr.setSheet(rs.getBoolean("is_sheet"));
                pr.setUserID(rs.getString("user_id"));
                pr.setPartName(rs.getString("part_name"));
                pr.setPartQuantity(rs.getInt("part_quantity"));
                pr.setPartUrl(rs.getString("part_url"));
                pr.setVendor(rs.getString("vendor"));
                pr.setNeedBy(rs.getDate("need_by"));
                pr.setPartNumber(rs.getString("part_number"));
                pr.setCost(rs.getDouble("cost"));
                pr.setTotalCost(rs.getDouble("total_cost"));
                pr.setJustification(rs.getString("justification"));
                pr.setApproved(rs.getBoolean("approved"));
                returnList.add(pr);
            }
            rs.close();
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
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
                    "'" + pr.getJustification() + "'," +
                    "'" + pr.isApproved() + "'" +
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
