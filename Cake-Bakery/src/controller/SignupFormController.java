package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.user;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class SignupFormController {
    public AnchorPane SignupFormContext;
    public PasswordField txtRePassword;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public TextField txtUserName;
    public TextField txtTelNo;
    public TextField txtUserID;

    public void SignUpOnAction(ActionEvent actionEvent) throws IOException {
        if (txtPassword.getText().equals(txtRePassword.getText())) {

                user us = new user(txtUserID.getText(), txtUserName.getText(),txtPassword.getText());
                try {
                    if (UserCrudController.signupUser(us)) {
                        txtUserID.clear();
                        txtUserName.clear();
                        txtEmail.clear();
                        txtPassword.clear();
                        txtRePassword.clear();

                        new Alert(Alert.AlertType.CONFIRMATION,"Saved!").show();

                        setUI("LoginForm");

                    } else {
                        new Alert(Alert.AlertType.ERROR,"Please Try Again!").show();

                    }
                } catch (SQLIntegrityConstraintViolationException e) {

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

        } else {
            new Alert(Alert.AlertType.ERROR,"Password is not matched..Please Try Again!").show();

        }


    }

    public void BackOnAction(ActionEvent actionEvent) throws IOException {
        setUI("LoginSignUpForm");
    }
    private void setUI(String location) throws IOException {
        Stage stage=(Stage) SignupFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }
}
