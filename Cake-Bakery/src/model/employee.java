package model;

public class employee {
    private String empId;
    private String empName;
    private String address;
    private String ContactNo;
    private String gender;

    public employee() {
    }

    public employee(String empId, String empName, String address, String contactNo, String gender) {
        this.empId = empId;
        this.empName = empName;
        this.address = address;
        ContactNo = contactNo;
        this.gender = gender;
    }



    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}