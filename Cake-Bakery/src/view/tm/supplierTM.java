package view.tm;

import javafx.scene.control.Button;

public class supplierTM {


    private String supplierCode;
    private String supplierName;
    private String Address;
    private String ContactNo;
    private Button btn1;

    public supplierTM() {
    }

    public supplierTM(String supplierCode, String supplierName, String address, String contactNo) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        Address = address;
        ContactNo = contactNo;
    }

    public supplierTM(String supplierCode, String supplierName, String address, String contactNo, Button btn1) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        Address = address;
        ContactNo = contactNo;
        this.btn1 = btn1;
    }

    @Override
    public String toString() {
        return "supplierTM{" +
                "supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", Address='" + Address + '\'' +
                ", ContactNo='" + ContactNo + '\'' +
                ", btn1=" + btn1 +
                '}';
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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
