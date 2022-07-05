package view.tm;

import javafx.scene.control.Button;

public class employeeTM {
    private String employeeId;
    private String employeeName;
    private String employeeAddress;
    private String empContactNo;
    private String employeeGender;
    private Button btn1;

    @Override
    public String toString() {
        return "employeeTM{" +
                "employeeId='" + employeeId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", employeeAddress='" + employeeAddress + '\'' +
                ", empContactNo='" + empContactNo + '\'' +
                ", employeeGender='" + employeeGender + '\'' +
                ", btn1=" + btn1 +
                '}';
    }

    public employeeTM() {
    }

    public employeeTM(String employeeId, String employeeName, String employeeAddress, String empContactNo, String employeeGender) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.empContactNo = empContactNo;
        this.employeeGender = employeeGender;
    }

    public employeeTM(String employeeId, String employeeName, String employeeAddress, String empContactNo, String employeeGender, Button btn1) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeAddress = employeeAddress;
        this.empContactNo = empContactNo;
        this.employeeGender = employeeGender;
        this.btn1 = btn1;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmpContactNo() {
        return empContactNo;
    }

    public void setEmpContactNo(String empContactNo) {
        this.empContactNo = empContactNo;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }
}
