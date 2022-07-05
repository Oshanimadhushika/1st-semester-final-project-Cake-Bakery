package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginSignUpFormController {
    public AnchorPane LoginSignUpFormContext;

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        setUI("LoginForm");
    }

    /*public void SignUpOnAction(ActionEvent actionEvent) throws IOException {
        setUI("signupForm");
    }*/
    private void setUI(String location) throws IOException {
        Stage stage=(Stage) LoginSignUpFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }
}
