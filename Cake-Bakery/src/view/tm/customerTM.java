package view.tm;

import javafx.scene.control.Button;



public class customerTM {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String ContactNo;
    private Button btn1;

    @Override
    public String toString() {
        return "customerTM{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", ContactNo='" + ContactNo + '\'' +
                ", btn1=" + btn1 +
                '}';
    }



    public customerTM() {
    }

    public customerTM(String customerId, String customerName, String customerAddress, String contactNo) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        ContactNo = contactNo;
    }

    public customerTM(String customerId, String customerName, String customerAddress, String contactNo, Button btn1) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        ContactNo = contactNo;
        this.btn1 = btn1;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }
}