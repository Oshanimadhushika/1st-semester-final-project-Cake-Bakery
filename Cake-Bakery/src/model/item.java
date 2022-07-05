package model;

import java.util.ArrayList;

public class item {
    private String itemId;
    private String name;
    private String description;
    private double unitPrice;
    private int qtyOnHand;
    private ArrayList<supplierOrderDetail> details;

    public item(String itemId, String name, String description, double unitPrice, int qtyOnHand, ArrayList<supplierOrderDetail> details) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
        this.details = details;
    }

    public item() {
    }

    public item(String itemId, String name, String description, double unitPrice,int qtyOnHand ) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;

    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ArrayList<supplierOrderDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<supplierOrderDetail> details) {
        this.details = details;
    }
}
