package view.tm;

import javafx.scene.control.Button;

public class cartTM {
    private String orderId;
    private String itemId;
    private String description;
    private Double unitPrice;
    private int qty;
    private double totalCost;
    private Button btn1;

    public cartTM() {
    }

    public cartTM(String orderId, String itemId, String description, Double unitPrice, int qty, double totalCost, Button btn1) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.totalCost = totalCost;
        this.btn1 = btn1;
    }

    public cartTM(String orderId, String itemId, String description, Double unitPrice, int qty, double totalCost) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "orderTM{" +
                "orderId='" + orderId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", totalCost=" + totalCost +
                ", btn1=" + btn1 +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }


    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }
}
