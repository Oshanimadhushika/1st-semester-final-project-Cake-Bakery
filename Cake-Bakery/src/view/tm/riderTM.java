package view.tm;

import javafx.scene.control.Button;

public class riderTM {

    private String deliverID;
    private String deliverName;
    private String ContactNo;
    private String vehiNo;
    private Button btn1;

    public riderTM(String deliverID, String deliverName, String contactNo, String vehiNo, Button btn1) {
        this.deliverID = deliverID;
        this.deliverName = deliverName;
        ContactNo = contactNo;
        this.vehiNo = vehiNo;
        this.btn1 = btn1;
    }

    public riderTM() {
    }

    public riderTM(String deliverID, String deliverName, String contactNo, String vehiNo) {
        this.deliverID = deliverID;
        this.deliverName = deliverName;
        ContactNo = contactNo;
        this.vehiNo = vehiNo;
    }

    public String getDeliverID() {
        return deliverID;
    }

    public void setDeliverID(String deliverID) {
        this.deliverID = deliverID;
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
        return vehiNo;
    }

    public void setVehiNo(String vehiNo) {
        this.vehiNo = vehiNo;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }
}
