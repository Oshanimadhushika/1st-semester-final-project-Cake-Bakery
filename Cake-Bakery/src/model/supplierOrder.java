package model;

public class supplierOrder {
    private String supplierOrderCode;
    private String Ingredients;
    private int qty;
    private double Cost;
    private String ManufacDate;
    private String ExpireDate;
    private String supplierCode;

    public supplierOrder() {
    }

    public supplierOrder(String supplierOrderCode, String ingredients, int qty, double cost, String manufacDate, String expireDate, String supplierCode) {
        this.supplierOrderCode = supplierOrderCode;
        Ingredients = ingredients;
        this.qty = qty;
        Cost = cost;
        ManufacDate = manufacDate;
        ExpireDate = expireDate;
        this.supplierCode = supplierCode;
    }

    public String getSupplierOrderCode() {
        return supplierOrderCode;
    }

    public void setSupplierOrderCode(String supplierOrderCode) {
        this.supplierOrderCode = supplierOrderCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
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

    public String getManufacDate() {
        return ManufacDate;
    }

    public void setManufacDate(String manufacDate) {
        ManufacDate = manufacDate;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }
}
