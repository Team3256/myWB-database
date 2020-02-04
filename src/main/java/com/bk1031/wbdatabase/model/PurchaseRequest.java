package com.bk1031.wbdatabase.model;

import java.util.Date;

public class PurchaseRequest {

    private String id;
    private boolean isSheet;
    private String userID;
    private String partName;
    private int partQuantity;
    private String partUrl;
    private String vendor;
    private Date needBy;
    private Date submittedOn;
    private String partNumber;
    private double cost;
    private double totalCost;
    private String justification;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSheet() {
        return isSheet;
    }

    public void setSheet(boolean sheet) {
        isSheet = sheet;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(int partQuantity) {
        this.partQuantity = partQuantity;
    }

    public String getPartUrl() {
        return partUrl;
    }

    public void setPartUrl(String partUrl) {
        this.partUrl = partUrl;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getNeedBy() {
        return needBy;
    }

    public void setNeedBy(Date needBy) {
        this.needBy = needBy;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(Date submittedOn) {
        this.submittedOn = submittedOn;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"isSheet\":" + isSheet + "," +
                "\"userID\":\"" + userID + "\"," +
                "\"partName\":\"" + partName + "\"," +
                "\"partQuantity\":" + partQuantity + "," +
                "\"partUrl\":\"" + partUrl + "\"," +
                "\"vendor\":\"" + vendor + "\"," +
                "\"needBy\":\"" + needBy + "\"," +
                "\"submittedOn\":\"" + submittedOn + "\"," +
                "\"partNumber\":\"" + partNumber + "\"," +
                "\"cost\":" + cost + "," +
                "\"totalCost\":" + totalCost + "," +
                "\"justification\":\"" + justification + "\"," +
                "\"status\":" + status + "" +
                "}";
    }
}
