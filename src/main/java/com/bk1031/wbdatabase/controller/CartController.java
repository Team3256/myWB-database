package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.model.Cart;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static spark.Spark.*;

public class CartController {

    private Connection db;

    Gson gson = new Gson();

    public CartController(Connection db) {
        this.db = db;
        getCart();
        createCart();
        deleteCart();
    }

    private void getCart() {
        get("/api/store/cart/:id", (request, response) -> {
            // Get Event
            List<Cart> returnList = new ArrayList<>();
            String sql = "SELECT * FROM \"cart\" WHERE user_id='" + request.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setProductID(rs.getString("product_id"));
                cart.setUserID(rs.getString("user_id"));
                cart.setProductName(rs.getString("product_name"));
                cart.setSize(rs.getString("size"));
                cart.setVariant(rs.getString("variant"));
                cart.setQuantity(rs.getInt("quantity"));
                cart.setPrice(rs.getInt("price"));
                returnList.add(cart);
            }
            rs.close();
            response.type("application/json");
            response.body(returnList.toString());
            return response;
        });
    }

    private void createCart() {
        post("/api/store/cart", (req, res) -> {
            Cart cart = gson.fromJson(req.body(), Cart.class);
            System.out.println("PARSED CART: " + cart);
            if (cart.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            String sql = "INSERT INTO \"cart\" VALUES " +
                    "(" +
                    "'" + cart.getProductID() + "'," +
                    "'" + cart.getUserID() + "'," +
                    "'" + cart.getProductName() + "'," +
                    "'" + cart.getSize() + "'," +
                    "'" + cart.getVariant() + "'," +
                    cart.getQuantity() + "," +
                    cart.getPrice() +
                    ")";
            db.createStatement().executeUpdate(sql);
            db.commit();
            System.out.println("Inserted records into the table...");
            res.type("application/json");
            res.body(cart.toString());
            return res;
        });
    }

    private void deleteCart() {
        delete("/api/store/cart/:userID/:productID", (req, res) -> {
            String sql = "delete from cart where user_id='" + req.params(":userID") + "' and product_id='" + req.params(":productID") + "';";
            db.createStatement().executeUpdate(sql);
            db.commit();
            res.status(200);
            return res;
        });
    }
}
