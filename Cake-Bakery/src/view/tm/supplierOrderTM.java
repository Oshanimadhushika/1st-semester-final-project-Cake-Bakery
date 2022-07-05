package view.tm;

import javafx.scene.control.Button;

public class supplierOrderTM {

    private String supplierCode;
    private String supplierOrderCode;
    private String Ingredients;
    private int qty;
    private double Cost;
    private Button btn1;

    public supplierOrderTM() {
    }

    /*public supplierOrderTM(String supplierCode, String supplierOrderCode, String ingredients, int qty, double cost) {
        this.supplierCode = supplierCode;
        this.supplierOrderCode = supplierOrderCode;
        Ingredients = ingredients;
        this.qty = qty;
        Cost = cost;
    }*/

    public supplierOrderTM(String supplierCode, String supplierOrderCode, String ingredients, int qty, double cost, Button btn1) {
        this.supplierCode = supplierCode;
        this.supplierOrderCode = supplierOrderCode;
        Ingredients = ingredients;
        this.qty = qty;
        Cost = cost;
        this.btn1 = btn1;
    }

    @Override
    public String toString() {
        return "supplierOrderTM{" +
                "supplierCode='" + supplierCode + '\'' +
                ", supplierOrderCode='" + supplierOrderCode + '\'' +
                ", Ingredients='" + Ingredients + '\'' +
                ", qty=" + qty +
                ", Cost=" + Cost +
                ", btn1=" + btn1 +
                '}';
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierOrderCode() {
        return supplierOrderCode;
    }

    public void setSupplierOrderCode(String supplierOrderCode) {
        this.supplierOrderCode = supplierOrderCode;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }
}