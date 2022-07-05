package model;

import java.util.ArrayList;

public class order {
    private String orderId;
    private String cId;
    private String dId;
    private String pId;
    private String orderDate;
    private double totalCost;
    private ArrayList<orderDetail> items;

    public order(String orderId, String cId, String dId, String pId, String orderDate, double totalCost) {
        this.orderId = orderId;
        this.cId = cId;
        this.dId = dId;
        this.pId = pId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
    }

    public order() {
    }

    public order(String orderId, String cId, String dId, String pId, String orderDate, double totalCost, ArrayList<orderDetail> items) {
        this.orderId = orderId;
        this.cId = cId;
        this.dId = dId;
        this.pId = pId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }


    public ArrayList<orderDetail> getItems() {
        return items;
    }

    public void setItems(ArrayList<orderDetail> items) {
        this.items = items;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
