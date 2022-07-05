package view.tm;

import javafx.scene.control.Button;

public class userTM {
    private String userId;
    private String userName;
    private String password;
    private Button btn1;

    @Override
    public String toString() {
        return "userTM{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", btn1=" + btn1 +
                '}';
    }

    public userTM() {
    }

    public userTM(String userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public userTM(String userId, String userName, String password, Button btn1) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.btn1 = btn1;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Button getBtn1() {
        return btn1;
    }

    public void setBtn1(Button btn1) {
        this.btn1 = btn1;
    }
}
