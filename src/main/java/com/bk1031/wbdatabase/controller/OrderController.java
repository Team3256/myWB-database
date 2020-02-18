package com.bk1031.wbdatabase.controller;

import com.bk1031.wbdatabase.Constants;
import com.bk1031.wbdatabase.model.Cart;
import com.bk1031.wbdatabase.model.Order;
import com.bk1031.wbdatabase.model.OrderItem;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class OrderController {

    private Connection db;

    Gson gson = new Gson();

    public OrderController(Connection db) {
        this.db = db;
        getOrder();
        createOrder();
    }

    private void getOrder() {
        get("/api/store/orders/:id", (request, response) -> {
            // Get Order
            Order order = new Order();
            String sql = "SELECT * FROM \"order\" WHERE order_id='" + request.params(":id") + "'";
            ResultSet rs = db.createStatement().executeQuery(sql);
            while (rs.next()) {
                order.setOrderID(rs.getString("order_id"));
                order.setUserID(rs.getString("user_id"));
                order.setPaymentComplete(rs.getBoolean("payment_complete"));
            }
            rs.close();
            // Get items in order
            String itemSql = "SELECT * FROM \"order_item\" WHERE order_id='" + request.params("id") + "'";
            ResultSet rs2 = db.createStatement().executeQuery(itemSql);
            while (rs2.next()) {
                OrderItem item = new OrderItem();
                item.setProductID(rs2.getString("product_id"));
                item.setOrderID(rs2.getString("order_id"));
                item.setProductName(rs2.getString("product_name"));
                item.setSize(rs2.getString("size"));
                item.setVariant(rs2.getString("variant"));
                item.setQuantity(rs2.getInt("quantity"));
                item.setPrice(rs2.getInt("price"));
                order.items.add(item);
            }
            rs2.close();
            if (order.toString().contains("null")) {
                response.status(404);
                response.type("application/json");
                response.body("{\"message\": \"Requested order not found\"}");
                return response;
            }
            response.type("application/json");
            response.body(order.toString());
            return response;
        });
    }

    private void createOrder() {
        post("/api/store/orders", (req, res) -> {
            Order order = gson.fromJson(req.body(), Order.class);
            System.out.println("PARSED ORDER: " + order);
            Stripe.apiKey = Constants.STRIPE_API_KEY;

            Map<String, Object> params = new HashMap<String, Object>();

            ArrayList<String> paymentMethodTypes = new ArrayList<>();
            paymentMethodTypes.add("card");
            params.put("payment_method_types", paymentMethodTypes);

            ArrayList<HashMap<String, Object>> lineItems = new ArrayList<>();
            for (OrderItem item : order.items) {
                HashMap<String, Object> lineItem = new HashMap<String, Object>();
                lineItem.put("name", item.getProductName());
                lineItem.put("description", "Size: " + item.getSize() + ", Variant: " + item.getVariant());
                lineItem.put("amount", item.getPrice());
                lineItem.put("currency", "usd");
                lineItem.put("quantity", item.getQuantity());
                lineItems.add(lineItem);
            }
            params.put("line_items", lineItems);

            params.put("success_url", "https://vcrobotics.net/#/store/checkout/success?session_id={CHECKOUT_SESSION_ID}");
            params.put("cancel_url", "https://vcrobotics.net/#/store/checkout/cancel");

            Session session = Session.create(params);
            order.setOrderID(session.getId());
            if (order.toString().contains("null")) {
                res.status(400);
                res.type("application/json");
                res.body("{\"message\": \"Request missing or contains null values\"}");
                return res;
            }
            String sql = "INSERT INTO \"order\" VALUES " +
                    "(" +
                    "'" + order.getOrderID() + "'," +
                    "'" + order.getUserID() + "'," +
                    order.isPaymentComplete() +
                    ")";
            db.createStatement().executeUpdate(sql);
            for (OrderItem item : order.items) {
                String itemSql = "INSERT INTO \"order_item\" VALUES " +
                        "(" +
                        "'" + item.getProductID() + "'," +
                        "'" + session.getId() + "'," +
                        "'" + item.getProductName() + "'," +
                        "'" + item.getSize() + "'," +
                        "'" + item.getVariant() + "'," +
                        item.getQuantity() + "," +
                        item.getPrice() +
                        ")";
                db.createStatement().executeUpdate(itemSql);
            }
            db.commit();
            System.out.println("Inserted records into the table...");
            res.type("application/json");
            res.body(order.toString());
            return res;
        });
    }

}