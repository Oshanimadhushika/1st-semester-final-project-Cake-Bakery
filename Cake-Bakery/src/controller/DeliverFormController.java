package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.rider;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.riderTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class DeliverFormController {
    public AnchorPane DeliverFormContext;
    public Button btnSaveDeliver;
    public TextField txtDeliverID;
    public TextField txtDeliverName;
    public TextField txtContactNo;
    public TextField txtVehiNo;
    public TableView tblDeliver;
    public TableColumn tblDeliverId;
    public TableColumn tblDeliverName;
    public TableColumn tblContactNo;
    public TableColumn tblVehiNo;
    public TableColumn tblColumnOption;
    public Button btnSearch;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(D00-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern contactPattern = Pattern.compile("^07(7|6|8|1|2|5|0|4)-[0-9]{7}$");
    Pattern vehicleNoPattern = Pattern.compile("^[A-Z]{2}[-][0-9]{4}$");

    public void initialize() throws SQLException, ClassNotFoundException {

        btnSaveDeliver.setDisable(true);
        ValidateUnit();

        tblDeliverId.setCellValueFactory(new PropertyValueFactory<>("deliverID"));
        tblDeliverName.setCellValueFactory(new PropertyValueFactory<>("deliverName"));
        tblContactNo.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
        tblVehiNo.setCellValueFactory(new PropertyValueFactory<>("vehiNo"));
        tblColumnOption.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        loadAllDelivers();
        tblDeliver.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    riderTM selectedItem = (riderTM) tblDeliver.getSelectionModel().getSelectedItem();
                    txtDeliverID.setText(selectedItem.getDeliverID());
                    txtDeliverName.setText(selectedItem.getDeliverName());
                    txtContactNo.setText(selectedItem.getContactNo());
                    txtVehiNo.setText(selectedItem.getVehiNo());


                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnSaveDeliver.setText("Update");
                //setData((RoomRM) newValue);
            }
        });

    }
    private void ValidateUnit() {
        btnSaveDeliver.setDisable(true);
        map.put(txtDeliverID,idPattern);
        map.put(txtDeliverName,namePattern);
        map.put(txtContactNo,contactPattern);
        map.put(txtVehiNo,vehicleNoPattern);
    }

    public void SaveDeliverOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if (btnSaveDeliver.getText().equals("Update")){
            rider d = new rider(
                    txtDeliverID.getText(),txtDeliverName.getText(), txtContactNo.getText(),txtVehiNo.getText());

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Deliver SET deliverName=? , ContactNo=? , VehiNo=?  WHERE deliverId=?",d.getDeliverName(),d.getContactNo(),d.getVehiNo(),d.getDeliverId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllDelivers();
                    btnSaveDeliver.setText("Save Deliver");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            rider d = new rider(
                    txtDeliverID.getText(),txtDeliverName.getText(), txtContactNo.getText(), txtVehiNo.getText()
            );

            try {
                if (CrudUtil.execute("INSERT INTO Deliver VALUES (?,?,?,?)",d.getDeliverId(),d.getDeliverName(),d.getContactNo(),d.getVehiNo())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllDelivers();
        }
    }
    private void loadAllDelivers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Deliver");
        ObservableList<riderTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1=new Button("Delete");
            btn1.setOnAction(event -> {
                riderTM selectedItem= (riderTM) tblDeliver.getSelectionModel().getSelectedItem();
                txtDeliverID.setText(selectedItem.getDeliverID());


                deleteCustomer();

                try {
                    loadAllDelivers();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new riderTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            btn1
                    )
            );

        }
        tblDeliver.setItems(obList);
        clearAllTexts();

    }

    private void deleteCustomer() {
        try{

            if(CrudUtil.execute("DELETE FROM Deliver WHERE deliverId=?",txtDeliverID.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllDelivers();
                btnSaveDeliver.setText("Save Deliver");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }

    private void clearAllTexts() {
        txtDeliverID.clear();
        txtDeliverName.clear();
        txtContactNo.clear();
        txtVehiNo.clear();
        txtDeliverID.requestFocus();
        setBorders(txtDeliverID,txtDeliverName,txtContactNo,txtVehiNo);


    }
    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }



    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result = (ResultSet) CrudUtil.execute("SELECT * FROM Deliver WHERE deliverId=?",txtDeliverID.getText());
        if (result.next()) {
            txtDeliverName.setText(result.getString(2));
            txtContactNo.setText(result.getString(3));
            txtVehiNo.setText(result.getString(4));

        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    public void SearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void deliverIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    public void txtFeild_Key_Released(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSaveDeliver);

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
