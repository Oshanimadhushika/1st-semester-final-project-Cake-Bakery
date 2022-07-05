package model;

public class supplier {
    private String supplierCode;
    private String supplierName;
    private String Address;
    private String ContactNo;

    public supplier() {
    }

    public supplier(String supplierCode, String supplierName, String address, String contactNo) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        Address = address;
        ContactNo = contactNo;
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
}

