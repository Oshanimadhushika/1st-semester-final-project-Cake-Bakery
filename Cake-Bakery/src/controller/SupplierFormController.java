package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.customer;
import model.employee;
import model.item;
import model.supplier;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.cartTM;
import view.tm.customerTM;
import view.tm.employeeTM;
import view.tm.supplierTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SupplierFormController {
    public AnchorPane SupplierFormContext;
    public Button btnADD;
    public TextField txtSupName;
    public TableView<supplierTM> tblSupply;
    public TableColumn tblColumnSupCode;
    public TableColumn tblColumnOption;
    public TextField txtSupCode;
    public Button btnSearch;
    public TableColumn tblColumnSuplierNamet;
    public TableColumn tblColumnAddress;
    public TableColumn tblColumnContactNo;
    public TextField txtAddress;
    public TextField txtContactNo;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern supCodePattern = Pattern.compile("^(S00-)[0-9]{3,5}$");
    Pattern supnamePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");


    public void initialize() throws SQLException, ClassNotFoundException {
        btnADD.setDisable(true);
        ValidateUnit();

        tblColumnSupCode.setCellValueFactory(new PropertyValueFactory<>("supplierCode"));
        tblColumnSuplierNamet.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        tblColumnAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tblColumnContactNo.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
        tblColumnOption.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        loadAllSuppliers();
        tblSupply.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    supplierTM selectedItem = (supplierTM) tblSupply.getSelectionModel().getSelectedItem();
                    txtSupCode.setText(selectedItem.getSupplierCode());
                    txtSupName.setText(selectedItem.getSupplierName());
                    txtAddress.setText(selectedItem.getAddress());
                    txtContactNo.setText(selectedItem.getContactNo());


                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnADD.setText("Update");

            }
        });

    }

    private void ValidateUnit() {
        btnADD.setDisable(true);
        map.put(txtSupCode,supCodePattern);
        map.put(txtSupName,supnamePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContactNo,contactPattern);
    }

    public void supplierCodeOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnADD.getText().equals("Update")){
            supplier sp = new supplier(
                    txtSupCode.getText(),txtSupName.getText(), txtAddress.getText(),txtContactNo.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Supplier SET supplierName=? , Address=? , ContactNo=?  WHERE supplierCode=?",sp.getSupplierName(),sp.getAddress(),sp.getContactNo(),sp.getSupplierCode());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllSuppliers();
                    btnADD.setText("+ ADD");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            supplier sp = new supplier(
                    txtSupCode.getText(),txtSupName.getText(), txtAddress.getText(), txtContactNo.getText()
            );

            try {
                if (CrudUtil.execute("INSERT INTO Supplier VALUES (?,?,?,?)",sp.getSupplierCode(),sp.getSupplierName(),sp.getAddress(),sp.getContactNo())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllSuppliers();
        }
    }

    private void loadAllSuppliers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier");
        ObservableList<supplierTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1=new Button("Delete");
            btn1.setOnAction(event -> {
                supplierTM selectedItem= (supplierTM) tblSupply.getSelectionModel().getSelectedItem();
                txtSupCode.setText(selectedItem.getSupplierCode());


                deleteSupplier() ;

                try {
                    loadAllSuppliers();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new supplierTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1
                    )
            );

        }
        tblSupply.setItems(obList);
        clearAllTexts();

    }

    private void deleteSupplier() {
        try{

            if(CrudUtil.execute("DELETE FROM Supplier WHERE supplierCode=?",txtSupCode.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllSuppliers();
                btnADD.setText("+ ADD");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }

    private void clearAllTexts() {
        txtSupCode.clear();
        txtSupName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        txtSupCode.requestFocus();
        setBorders(txtSupCode,txtSupName,txtAddress,txtContactNo);


    }

    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }



    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result = (ResultSet) CrudUtil.execute("SELECT * FROM Supplier WHERE supplierCode=?",txtSupCode.getText());
        if (result.next()) {
            txtSupName.setText(result.getString(2));
            txtAddress.setText(result.getString(3));
            txtContactNo.setText(result.getString(4));

        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
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
