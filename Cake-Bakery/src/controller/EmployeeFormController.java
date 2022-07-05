package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import model.employee;
import util.CrudUtil;

import util.ValidationUtil;
import view.tm.employeeTM;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public AnchorPane EmployeeFormContext;
    public Button btnADD;
    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public ComboBox cmbGender;
    public TableView<employeeTM> tblEmployee;
    public TableColumn tblColumId;
    public TableColumn tblColumnName;
    public TableColumn tblColumnAddress;
    public TableColumn tblColumnContact;
    public TableColumn tblColumnGender;
    public TextField txtContact;
    public TableColumn tblColumnOption;
    public Button btnSearch;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(E00-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");

    public void initialize() throws SQLException, ClassNotFoundException {

        btnADD.setDisable(true);
        ValidateUnit();

        ObservableList <String> obl =FXCollections.observableArrayList();

        obl.add ("Male");
        obl.add("Female");

        cmbGender.setItems (obl);

        //cmbGender.getItems().addAll("Male","Female");
        //cmbGender.getItems().addAll("Female");

        tblColumId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        tblColumnName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        tblColumnAddress.setCellValueFactory(new PropertyValueFactory<>("employeeAddress"));
        tblColumnContact.setCellValueFactory(new PropertyValueFactory<>("empContactNo"));
        tblColumnGender.setCellValueFactory(new PropertyValueFactory<>("employeeGender"));
        tblColumnOption.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        loadAllEmployees();
        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    employeeTM selectedItem = (employeeTM) tblEmployee.getSelectionModel().getSelectedItem();
                    txtID.setText(selectedItem.getEmployeeId());
                    txtName.setText(selectedItem.getEmployeeName());
                    txtAddress.setText(selectedItem.getEmployeeAddress());
                    txtContact.setText(selectedItem.getEmpContactNo());
                    cmbGender.setValue(selectedItem.getEmployeeGender());

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
        map.put(txtID,idPattern);
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContact,contactPattern);
    }
    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnADD.getText().equals("Update")){
            employee em = new employee(
                    txtID.getText(),txtName.getText(), txtAddress.getText(),txtContact.getText(),(String) cmbGender.getValue());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Employee SET empName=? , address=? , ContactNo=? , gender=? WHERE empId=?",em.getEmpName(),em.getAddress(),em.getContactNo(),em.getGender(),em.getEmpId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllEmployees();
                    btnADD.setText("+ ADD");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            employee em = new employee(
                    txtID.getText(),txtName.getText(), txtAddress.getText(), txtContact.getText(), (String) cmbGender.getValue()
            );

            try {
                if (CrudUtil.execute("INSERT INTO Employee VALUES (?,?,?,?,?)",em.getEmpId(),em.getEmpName(),em.getAddress(),em.getContactNo(),em.getGender())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllEmployees();
        }
    }

    private void loadAllEmployees() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Employee");
        ObservableList<employeeTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1=new Button("Delete");
            btn1.setOnAction(event -> {
                employeeTM selectedItem= (employeeTM) tblEmployee.getSelectionModel().getSelectedItem();
                txtID.setText(selectedItem.getEmployeeId());


                deleteEmployee();

                try {
                    loadAllEmployees();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new employeeTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            btn1
                    )
            );

        }
        tblEmployee.setItems(obList);
        clearAllTexts();

    }

    private void deleteEmployee() {
        try{

            if(CrudUtil.execute("DELETE FROM Employee WHERE empId=?",txtID.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllEmployees();
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
        ResultSet result = (ResultSet) CrudUtil.execute("SELECT * FROM Employee WHERE empId=?",txtID.getText());
        if (result.next()) {
            txtName.setText(result.getString(2));
            txtAddress.setText(result.getString(3));
            txtContact.setText(result.getString(4));
            cmbGender.setValue(result.getString(5));

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

    public void employeeIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
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
