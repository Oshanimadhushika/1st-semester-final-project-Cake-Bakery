package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.item;
import model.user;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.itemTM;
import view.tm.userTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class UserFormController {
    public AnchorPane UserFormContext;
    public Button btnADD;
    public TextField txtID;
    public TextField txtName;
    public TableView tblUser;
    public TableColumn tblColumId;
    public TableColumn tblColumnName;
    public TableColumn tblColumnOption;
    public TableColumn tblColumnPassword;
    public TextField txtPassword;
    public Button btnSearch;
    public TextField txtEmail;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(U00-)[0-9]{3,5}$");
    Pattern usernamePattern = Pattern.compile("^[A-z ]{4,25}$");
    Pattern emailPattern=Pattern.compile("^[A-z 0-9]{3,8}@(gmail).com");
    Pattern passwordPattern= Pattern.compile("^[A-z]{3,8}[0-9]{4}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        btnADD.setDisable(true);
        ValidateUnit();
        tblColumId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tblColumnName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tblColumnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        tblColumnOption.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        loadAllUsers();
        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    userTM selectedItem = (userTM) tblUser.getSelectionModel().getSelectedItem();
                    txtID.setText(selectedItem.getUserId());
                    txtName.setText(selectedItem.getUserName());
                    txtPassword.setText(selectedItem.getPassword());


                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnADD.setText("Update");
                //setData((RoomRM) newValue);
            }
        });

    }

    private void ValidateUnit() {
        btnADD.setDisable(true);
        map.put(txtID,idPattern);
        map.put(txtName,usernamePattern);
        map.put(txtEmail,emailPattern);
        map.put(txtPassword,passwordPattern);


    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnADD.getText().equals("Update")){
            user u = new user(
                    txtID.getText(),txtName.getText(), txtPassword.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE User SET userName=? , password=?  WHERE userId=?",u.getUserName(),u.getPassword(),u.getUserId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllUsers();
                    btnADD.setText("+ ADD");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            user u = new user(
                    txtID.getText(),txtName.getText(), txtPassword.getText()
            );

            try {
                if (CrudUtil.execute("INSERT INTO User VALUES (?,?,?)",u.getUserId(),u.getUserName(),u.getPassword())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllUsers();
        }
    }
    private void loadAllUsers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM User");
        ObservableList<userTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1=new Button("Delete");
            btn1.setOnAction(event -> {
                userTM selectedItem= (userTM) tblUser.getSelectionModel().getSelectedItem();
                txtID.setText(selectedItem.getUserId());


                deleteUser();

                try {
                    loadAllUsers();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new userTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),

                            btn1
                    )
            );

        }
        tblUser.setItems(obList);
        clearAllTexts();

    }

    private void deleteUser() {
        try{

            if(CrudUtil.execute("DELETE FROM User WHERE userId=?",txtID.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllUsers();
                btnADD.setText("+ ADD");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }

    private void clearAllTexts() {
        txtID.clear();
        txtName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtID.requestFocus();
        setBorders(txtID,txtName,txtPassword);


    }
    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }



    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result = (ResultSet) CrudUtil.execute("SELECT * FROM User WHERE userId=?",txtID.getText());
        if (result.next()) {
            txtName.setText(result.getString(2));
            txtPassword.setText(result.getString(3));

        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }



    public void SearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void userIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void txtFeild_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnADD);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField errorText = (TextField) response;
                errorText.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.INFORMATION, "Aded").showAndWait();
            }
        }
    }
}
