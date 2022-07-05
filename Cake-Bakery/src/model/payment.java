package model;

public class payment {
    private String payId;
    private String payMethod;
    private String payDetail;

    public payment() {
    }

    public payment(String payId, String payMethod, String payDetail) {
        this.payId = payId;
        this.payMethod = payMethod;
        this.payDetail = payDetail;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(String payDetail) {
        this.payDetail = payDetail;
    }
}
