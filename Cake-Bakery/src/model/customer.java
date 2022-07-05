package model;

public class customer {
    private String cusId;
    private String cusName;
    private String address;
    private String ContactNo;
    private String uId;

    public customer() {
    }

    public customer(String cusId, String cusName, String address, String contactNo, String uId) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.address = address;
        ContactNo = contactNo;
        this.uId = uId;
    }

    public customer(String cusId, String cusName, String address, String contactNo) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.address = address;
        ContactNo = contactNo;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
