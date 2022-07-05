package view.tm;

public class ShowOrderDetailTM {
    private String orderId;
    private String customerId;
    private String deliverId;
    private String payId;
    private String orderDate;
    private double totalCost;

    public ShowOrderDetailTM() {
    }

    public ShowOrderDetailTM(String orderId, String customerId, String deliverId, String payId, String orderDate, double totalCost) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.deliverId = deliverId;
        this.payId = payId;
        this.orderDate = orderDate;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "ShowOrderDetailTM{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", deliverId='" + deliverId + '\'' +
                ", payId='" + payId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", totalCost=" + totalCost +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
