package view.tm;

public class SupplierOrderSmallTM {
    private String supplierOrderCode;
    private String ingredient;
    private int qty;



    public SupplierOrderSmallTM() {
    }

    public SupplierOrderSmallTM(String supplierOrderCode, String ingredient, int qty) {
        this.supplierOrderCode = supplierOrderCode;
        this.ingredient = ingredient;
        this.qty = qty;
    }

    public String getSupplierOrderCode() {
        return supplierOrderCode;
    }

    public void setSupplierOrderCode(String supplierOrderCode) {
        this.supplierOrderCode = supplierOrderCode;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
