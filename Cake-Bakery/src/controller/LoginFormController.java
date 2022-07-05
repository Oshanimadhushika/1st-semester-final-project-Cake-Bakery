package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CrudUtil;

import javax.security.auth.login.LoginContext;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane LoginFormContext;
    public PasswordField txtPassword;
    public TextField txtUserName;
    public Button btnLogin;
    static String userId;

    /*public void initialize(){
        btnLogin.setDisable(true);
    }*/

    //int attempts=0;
    public void LoginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        boolean yes=false;
        ResultSet result = CrudUtil.execute("SELECT * FROM User ");
        while (result.next()) {

            if (txtUserName.getText().equals(result.getString(2)) && txtPassword.getText().equals(result.getString(3))) {

                userId=result.getString(1);
                btnLogin.setDisable(false);
                yes=true;
                new Alert(Alert.AlertType.CONFIRMATION, "Your Logging is Successfull!..").show();
                setUI("DashBoardForm");


               // return;
            }

        }
        if (!yes){
            new Alert(Alert.AlertType.WARNING, "Your UserName Or Password Is Not Matched..Try Again!..").show();
            txtUserName.clear();
            txtPassword.clear();
        }

        }




   /* public void SignUpOnMouseClick(MouseEvent mouseEvent) throws IOException {
        setUI("signupForm");
    }*/
    private void setUI(String location) throws IOException {
        Stage stage=(Stage) LoginFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }
}
