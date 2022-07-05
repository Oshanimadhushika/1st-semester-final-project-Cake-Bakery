package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.supplier;
import model.supplierOrder;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.supplierOrderTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SupplierorderFormController {
    public AnchorPane SupplierOrderFormContext;
    public Button btnADD;
    public TableView <supplierOrderTM>tblSupplyOrder;
    public TableColumn tblColumnSupOrderCode;
    public TableColumn tblColumnIngredients;
    public TableColumn tblColumnUnitPrice;
    public TableColumn tblColumnQty;
    public TableColumn tblColumnTotalCost;
    public TableColumn tblColumnOption;
    public ComboBox cmbISupplierCode;
    public TextField txtManuDate;
    public TextField txtExDate;
    public TextField txtIngredients;
    public TextField txtSupOrderCode;
    public TextField txtQty;
    public Button btnSearch;
    public TextField txtISupplierName;
    public TableColumn tblColumnCost;
    public TextField txtCost;
    public TableColumn tblColumSupplierCode;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern supOrderCodePattern = Pattern.compile("^(SC00-)[0-9]{3,5}$");
    Pattern ingredientPattern = Pattern.compile("^[A-z0-9 ,/]{4,30}$");
    Pattern qtyPattern= Pattern.compile("^[0-9]{1,5}$");
    Pattern costPattern = Pattern.compile("^[0-9]{3,5}$");
    public void initialize() throws SQLException, ClassNotFoundException {
        btnADD.setDisable(true);
        ValidateUnit();
        setSupplierIds();
        tblColumSupplierCode.setCellValueFactory(new PropertyValueFactory<>("supplierCode"));
        tblColumnSupOrderCode.setCellValueFactory(new PropertyValueFactory<>("supplierOrderCode"));
        tblColumnIngredients.setCellValueFactory(new PropertyValueFactory<>("Ingredients"));
        tblColumnQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblColumnCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        tblColumnOption.setCellValueFactory(new PropertyValueFactory<>("btn1"));
        loadAllSupplierOrders();


        tblSupplyOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    supplierOrderTM selectedItem = (supplierOrderTM)
                    tblSupplyOrder.getSelectionModel().getSelectedItem();
                    cmbISupplierCode.setValue(selectedItem.getSupplierCode());
                    txtSupOrderCode.setText(selectedItem.getSupplierOrderCode());
                    txtIngredients.setText(selectedItem.getIngredients());
                    txtQty.setText(String.valueOf(selectedItem.getQty()));
                    txtCost.setText(String.valueOf(selectedItem.getCost()));




                    search();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                btnADD.setText("Update");

            }
        });

        cmbISupplierCode.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setSupplierDetails((String) newValue);
                });

 }

    private void ValidateUnit() {
        btnADD.setDisable(true);
        map.put(txtSupOrderCode,supOrderCodePattern);
        map.put(txtIngredients,ingredientPattern);
        map.put(txtQty,qtyPattern);
        map.put(txtCost,costPattern);

    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        //System.out.println(btnADD.getText());
        if (btnADD.getText().equals("Update")){
            supplierOrder so = new supplierOrder(
                    txtSupOrderCode.getText(),
                    txtIngredients.getText(),
                    Integer.parseInt(txtQty.getText()),
                    Double.valueOf(txtCost.getText()),
                    txtManuDate.getText(),
                    txtExDate.getText(),
                    (String)cmbISupplierCode.getValue()
            );
            //System.out.println(so.getSupplierOrderCode());
            try{
                boolean isUpdated = CrudUtil.execute("UPDATE SupplierOrder SET Ingredients=?,qty=?,cost=?,ManufacDate=?,ExpireDate=?,supplierCode=? WHERE supplierOrderCode=?",so.getIngredients(),so.getQty(),so.getCost(),so.getManufacDate(),so.getExpireDate(),so.getSupplierCode(),so.getSupplierOrderCode());

                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllSupplierOrders();
                    btnADD.setText("+ ADD");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            supplierOrder so = new supplierOrder(
                    txtSupOrderCode.getText(),
                    txtIngredients.getText(),
                    Integer.parseInt(txtQty.getText()),
                    Double.valueOf(txtCost.getText()),
                    txtManuDate.getText(),
                    txtExDate.getText(),
                    (String)cmbISupplierCode.getValue()
            );

            try {
                if (CrudUtil.execute("INSERT INTO SupplierOrder VALUES (?,?,?,?,?,?,?)",so.getSupplierOrderCode(),so.getIngredients(),so.getQty(),so.getCost(),so.getManufacDate(),so.getExpireDate(),so.getSupplierCode())){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllSupplierOrders();
        }
    }

    ObservableList<supplierOrderTM> tmList=FXCollections.observableArrayList();
    private void loadAllSupplierOrders() throws SQLException, ClassNotFoundException {


        ResultSet result = CrudUtil.execute("SELECT * FROM SupplierOrder");
        ObservableList<supplierOrderTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1=new Button("Delete");
            btn1.setOnAction(event -> {
                supplierOrderTM selectedItem= (supplierOrderTM) tblSupplyOrder.getSelectionModel().getSelectedItem();
                txtSupOrderCode.setText(selectedItem.getSupplierOrderCode());


                        deleteSupplierOrder();

                try {
                   // updateQuentity((String) txtSupOrderCode.getText(),Integer.parseInt(txtQty.getText()));

                    loadAllSupplierOrders();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }


            /*int rowNum=isExists(od);
            if (rowNum==-1){
                tmList.add(od);
            }else {
                cartTM temp = tmList.get(rowNum);
                cartTM newTm = new cartTM(temp.getOrderId(),temp.getItemId(),temp.getDescription(),temp.getUnitPrice(),Integer.valueOf((temp.getQty())+Integer.parseInt(txtQty.getText())),temp.getTotalCost(),temp.getBtn1());
                tmList.remove(rowNum);
                tmList.add(newTm);*/
        });
            obList.add(
                    new supplierOrderTM(
                            result.getString(7),
                            result.getString(1),
                            result.getString(2),
                            result.getInt(3),
                            result.getDouble(4),
                            btn1
                    )
            );

        }
        tblSupplyOrder.setItems(obList);
        clearAllTexts();

    }
    /*private Boolean updateQuentity(String supplierOrderCode,int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection()
                .prepareStatement
                        ("UPDATE SupplierOder SET qty=(qty+" + qty + ") WHERE supplierOrderCode='" + supplierOrderCode + "'");
        return stm.executeUpdate()>0;
    }
    private int isExists(cartTM tm){
        for (int i=0;i<tmList.size();i++){
            if (tm.getItemId().equals(tmList.get(i).getSupplierOrderCode())){
                return i;
            }
        }
        return -1;
    }*/

    private void deleteSupplierOrder() {
        try{

            if(CrudUtil.execute("DELETE FROM SupplierOrder WHERE supplierOrderCode=?",txtSupOrderCode.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllSupplierOrders();
                btnADD.setText("+ ADD");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }

    private void clearAllTexts() {
        txtSupOrderCode.clear();
        txtIngredients.clear();
        txtQty.clear();
        txtCost.clear();
        txtManuDate.clear();
        txtExDate.clear();
        txtSupOrderCode.requestFocus();
        setBorders(txtSupOrderCode,txtIngredients,txtQty,txtCost);


    }

    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }



    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result = (ResultSet) CrudUtil.execute("SELECT * FROM SupplierOrder WHERE supplierOrderCode=?",txtSupOrderCode.getText());
        if (result.next()) {
            txtIngredients.setText(result.getString(2));
            txtQty.setText(result.getString(3));
            txtCost.setText(result.getString(4));
            txtManuDate.setText(result.getString(5));
            txtExDate.setText(result.getString(6));


        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }
    private void setSupplierDetails(String selectedSupplierId) {
        try {
            supplier s=supplierCrudController.getSupplier(selectedSupplierId);
            if (s!=null){
                txtISupplierName.setText(s.getSupplierName());


            }else {
                new Alert(Alert.AlertType.WARNING,"Empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void setSupplierIds() {
        try {
            ObservableList<String> supidOblist= FXCollections.observableArrayList(
                    supplierCrudController.getSupplierIDs()
            );
            cmbISupplierCode.setItems(supidOblist);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
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

    public void supplierOrderCodeOnAction(ActionEvent actionEvent) {
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
