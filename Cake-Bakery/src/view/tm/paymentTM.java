package view.tm;

import javafx.scene.control.Button;

public class paymentTM {
    private String payId;
    private String payDetail;
    private String payMethod;
    private Button btn1;

    public paymentTM() {
    }

    public paymentTM(String payId, String payDetail, String payMethod) {
        this.payId = payId;
        this.payDetail = payDetail;
        this.payMethod = payMethod;
    }

    public paymentTM(String payId, String payDetail, String payMethod, Button btn1) {
        this.payId = payId;
        this.payDetail = payDetail;
        this.payMethod = payMethod;
        this.btn1 = btn1;
    }

    @Override
    public String toString() {
        return "paymentTM{" +
                "payId='" + payId + '\'' +
                ", payDetail='" + payDetail + '\'' +
                ", payMethod='" + payMethod + '\'' +
                ", btn1=" + btn1 +
                '}';
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(String payDetail) {
        this.payDetail = payDetail;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }
}
