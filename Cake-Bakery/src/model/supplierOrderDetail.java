package model;

public class supplierOrderDetail {
    private String supplierOrdercode;
    private String itemId;
    private int ingredientQty;
    private String price;

    public supplierOrderDetail(String supplierOrdercode, String itemId, int qty, String price) {
        this.supplierOrdercode = supplierOrdercode;
        this.itemId = itemId;
        this.ingredientQty = qty;
        this.price = price;
    }

    public supplierOrderDetail() {
    }

    public String getSupplierOrdercode() {
        return supplierOrdercode;
    }

    public void setSupplierOrdercode(String supplierOrdercode) {
        this.supplierOrdercode = supplierOrdercode;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getIngredientQty() {
        return ingredientQty;
    }

    public void setIngredientQty(int ingredientQty) {
        this.ingredientQty = ingredientQty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
