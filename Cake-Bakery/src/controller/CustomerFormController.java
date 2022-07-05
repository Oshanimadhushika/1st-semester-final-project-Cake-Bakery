package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.customer;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.customerTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class CustomerFormController {
    public AnchorPane CustomerFormContext;
    public Button btnSaveCustomer;
    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtContact;
    public TableColumn tblColumnName;
    public TableColumn tblColumnAddress;
    public TableColumn tblColumId;
    public TableColumn tblColumnContact;
    public TableColumn tblColumnOption;
    public TableView<customerTM> tblCustomer;
    public Button btnSearch;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(C00-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        btnSaveCustomer.setDisable(true);
       ValidateUnit();

        tblColumId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        tblColumnName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tblColumnAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        tblColumnContact.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
        tblColumnOption.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        loadAllCustomers();
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    customerTM selectedItem = (customerTM) tblCustomer.getSelectionModel().getSelectedItem();
                    txtID.setText(selectedItem.getCustomerId());
                    txtName.setText(selectedItem.getCustomerName());
                    txtAddress.setText(selectedItem.getCustomerAddress());
                    txtContact.setText(selectedItem.getContactNo());

                   search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnSaveCustomer.setText("Update");
                //setData((RoomRM) newValue);
            }
        });

    }

    private void ValidateUnit() {
        btnSaveCustomer.setDisable(true);
        map.put(txtID,idPattern);
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContact,contactPattern);
    }


    public void SaveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String userId=LoginFormController.userId;
        if (btnSaveCustomer.getText().equals("Update")){
            customer c = new customer(
                    txtID.getText(),txtName.getText(), txtAddress.getText(),txtContact.getText(),userId);

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Customer SET cusName=?,address=?,ContactNo=? WHERE cusId=?",c.getCusName(),c.getAddress(),c.getContactNo(),c.getCusId());

                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllCustomers();
                    btnSaveCustomer.setText("Save Customer");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            customer c = new customer(
                    txtID.getText(),txtName.getText(), txtAddress.getText(), txtContact.getText(),userId
            );

            try {
                if (CrudUtil.execute("INSERT INTO Customer VALUES (?,?,?,?,?)",c.getCusId(),c.getCusName(),c.getAddress(),c.getContactNo(),c.getuId())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllCustomers();
        }
    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<customerTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1=new Button("Delete");
            btn1.setOnAction(event -> {
                customerTM selectedItem= (customerTM) tblCustomer.getSelectionModel().getSelectedItem();
                txtID.setText(selectedItem.getCustomerId());


                deleteCustomer();

                try {
                    loadAllCustomers();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new customerTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1
                    )
            );

        }
        tblCustomer.setItems(obList);
        clearAllTexts();

    }

    private void deleteCustomer() {
        try{

            if(CrudUtil.execute("DELETE FROM Customer WHERE cusId=?",txtID.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllCustomers();
                btnSaveCustomer.setText("Save Customer");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }

    private void clearAllTexts() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtID.requestFocus();
        setBorders(txtID,txtName,txtAddress,txtContact);



    }

    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }




    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result = (ResultSet) CrudUtil.execute("SELECT * FROM Customer WHERE cusId=?",txtID.getText());
        if (result.next()) {
            txtName.setText(result.getString(2));
            txtAddress.setText(result.getString(3));
            txtContact.setText(result.getString(4));
        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    public void CustomerIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void SearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void txtFeild_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveCustomer);

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







