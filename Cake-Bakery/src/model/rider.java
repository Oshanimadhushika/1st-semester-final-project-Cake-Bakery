package model;

public class rider {
    private String deliverId;
    private String deliverName;
    private String ContactNo;
    private String VehiNo;

    public rider() {
    }

    public rider(String deliverId, String deliverName, String contactNo, String vehiNo) {
        this.deliverId = deliverId;
        this.deliverName = deliverName;
        this.ContactNo = contactNo;
        this.VehiNo = vehiNo;
    }



    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getDeliverName() {
        return deliverName;
    }

    public void setDeliverName(String deliverName) {
        this.deliverName = deliverName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getVehiNo() {
        return VehiNo;
    }

    public void setVehiNo(String vehiNo) {
        VehiNo = vehiNo;
    }
}
