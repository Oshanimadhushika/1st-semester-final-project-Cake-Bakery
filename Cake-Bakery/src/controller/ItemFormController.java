package controller;

import com.jfoenix.controls.JFXComboBox;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.item;
import model.orderDetail;
import model.supplierOrderDetail;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.SupplierOrderSmallTM;
import view.tm.cartTM;
import view.tm.itemTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ItemFormController {
    public AnchorPane ItemFormContext;
    public Button btnADD;
    public TextField txtName;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TableView<itemTM> tblItem;
    public TableColumn tblColumId;
    public TableColumn tblColumnName;
    public TableColumn tblColumnDescription;
    public TableColumn tblColumnUnitPrice;
    public TableColumn tblColumnQty;
    public TableColumn tblColumnOption;
    public TextField txtQty;
    public TextField txtItemId;
    public Button btnSearch;
    public TableColumn tblColumnQtyOnHand;
    public TableView tblSupOrderTable;
    public TableColumn colsupOrderCode;
    public TableColumn colSupOrderName;
    public Button btnADDSupplierOrder;
    public JFXComboBox cmbSupplierOrderCode;
    public TableColumn colIngredientName;
    public TextField txtIngredientQty;


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(I00-)[0-9]{3,5}$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,15}$");
    Pattern discriptionPattern = Pattern.compile("^[A-z ]{4,30}$");
    Pattern unitPricePattern = Pattern.compile("^[0-9]{3,5}$");
    Pattern qtyonhandPattern= Pattern.compile("^[0-9]{1,5}$");
    Pattern ingredientQtyPattern= Pattern.compile("^[0-9]{1,5}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        btnADD.setDisable(true);
        ValidateUnit();

        tblColumId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        tblColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tblColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblColumnUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblColumnQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblColumnOption.setCellValueFactory(new PropertyValueFactory<>("btn1"));

        colsupOrderCode.setCellValueFactory(new PropertyValueFactory<>("supplierOrderCode"));
        colIngredientName.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
        loadAllItems();
        loadIngredientIds();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    itemTM selectedItem = (itemTM) tblItem.getSelectionModel().getSelectedItem();
                    txtItemId.setText(selectedItem.getItemId());
                    txtName.setText(selectedItem.getName());
                    txtDescription.setText(selectedItem.getDescription());
                    txtUnitPrice.setText(selectedItem.getUnitPrice());
                    txtQty.setText(selectedItem.getQtyOnHand());

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
        map.put(txtItemId,idPattern);
        map.put(txtName,namePattern);
        map.put(txtDescription,discriptionPattern);
        map.put(txtUnitPrice,unitPricePattern);
        map.put(txtQty,qtyonhandPattern);
        map.put(txtIngredientQty,ingredientQtyPattern);
    }

    public void AddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<supplierOrderDetail> details = new ArrayList<>();
        for (SupplierOrderSmallTM temp : ingradients) {
            details.add(new supplierOrderDetail(temp.getSupplierOrderCode(), txtItemId.getText(),Integer.parseInt( txtIngredientQty.getText()),txtUnitPrice.getText()));
        }

        if (btnADD.getText().equals("Update")){
            item i = new item(
                    txtItemId.getText(),txtName.getText(), txtDescription.getText(),Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQty.getText()));

            try{
                boolean isUpdated = CrudUtil.execute("UPDATE Item SET name=? , description=? , unitPrice=? , qtyOnHand=? WHERE itemId=?",i.getName(),i.getDescription(),i.getUnitPrice(),i.getQtyOnHand(),i.getItemId());
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated!").show();
                    loadAllItems();
                    btnADD.setText("+ ADD Item");
                }else{
                    new Alert(Alert.AlertType.WARNING, "Try Again!").show();
                }


            }catch (SQLException | ClassNotFoundException e){

            }
        }else {
            item i = new item(
                    txtItemId.getText(),txtName.getText(), txtDescription.getText(), Double.parseDouble(txtUnitPrice.getText()),Integer.parseInt(txtQty.getText()),details
            );

            try {

                if (SaveItem(i)){
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();

                }

            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

            clearAllTexts();
            loadAllItems();
        }
    }
    private void loadAllItems() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Item");
        ObservableList<itemTM> obList = FXCollections.observableArrayList();


        while (result.next()) {
            Button btn1=new Button("Delete");
            btn1.setOnAction(event -> {
                itemTM selectedItem= (itemTM) tblItem.getSelectionModel().getSelectedItem();
                txtItemId.setText(selectedItem.getItemId());


                deleteCustomer();

                try {
                    loadAllItems();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });

            obList.add(
                    new itemTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            btn1
                    )
            );

        }
        tblItem.setItems(obList);
        clearAllTexts();

    }

    private void deleteCustomer() {
        try{

            if(CrudUtil.execute("DELETE FROM Item WHERE itemId=?",txtItemId.getText())){
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!").show();
                loadAllItems();
                btnADD.setText("+ ADD Item");
            }else{
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        }catch (SQLException | ClassNotFoundException e){

        }

    }

    private void clearAllTexts() {
        txtItemId.clear();
        txtName.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQty.clear();
        txtItemId.requestFocus();
        setBorders(txtItemId,txtName,txtDescription,txtUnitPrice,txtQty,txtIngredientQty);


    }

    public void setBorders(TextField... textFields){
        for (TextField textField : textFields) {
            textField.getParent().setStyle("-fx-border-color: rgba(76, 73, 73, 0.83)");
        }
    }



    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result = (ResultSet) CrudUtil.execute("SELECT * FROM Item WHERE itemId=?",txtItemId.getText());
        if (result.next()) {
            txtName.setText(result.getString(2));
            txtDescription.setText(result.getString(3));
            txtUnitPrice.setText(result.getString(4));
            txtQty.setText(result.getString(5));
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

    public void itemIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException |SQLException e) {
            e.printStackTrace();
        }
    }

    ObservableList<SupplierOrderSmallTM> ingradients=FXCollections.observableArrayList();
    public void AddSupplierOrderOnAction(ActionEvent actionEvent) {
        tblSupOrderTable.refresh();
        btnADD.setDisable(false);

        String ingredient=getIngredient((String) cmbSupplierOrderCode.getValue());
        SupplierOrderSmallTM item_ingradientTM=new SupplierOrderSmallTM((String) cmbSupplierOrderCode.getValue(),ingredient,Integer.parseInt(txtIngredientQty.getText()));

        ingradients.add(item_ingradientTM);
        tblSupOrderTable.setItems(ingradients);
    }
    public String getIngredient(String id){
        try {
            PreparedStatement stm= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM SupplierOrder WHERE supplierOrderCode=?");
            stm.setString(1,id);
            ResultSet rst= stm.executeQuery();

            if (rst.next()){
                return rst.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void loadIngredientIds() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT * FROM SupplierOrder");
        ArrayList<String> codeList = new ArrayList<>();
        while (result.next()){
            codeList.add(result.getString(1));
        }
        cmbSupplierOrderCode.getItems().addAll(codeList);

    }

    public boolean SaveItem(item itm) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO Item VALUES (?,?,?,?,?)");
            stm.setObject(1, itm.getItemId());
            stm.setObject(2, itm.getName());
            stm.setObject(3, itm.getDescription());
            stm.setObject(4, itm.getUnitPrice());
            stm.setObject(5, itm.getQtyOnHand());


            if (stm.executeUpdate() > 0) {

                if (saveSupplierOrderDetail(itm.getItemId(),itm.getDetails())) {
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }
    private boolean saveSupplierOrderDetail(String itemID, ArrayList<supplierOrderDetail> details) throws SQLException, ClassNotFoundException {
        for (supplierOrderDetail temp:details) {
            if (CrudUtil.execute("INSERT INTO SupplierOrder_detail VALUES(?,?,?,?)",temp.getSupplierOrdercode(),itemID,temp.getIngredientQty(),temp.getPrice())){

            }else {
                return false;
            }
        }
        return true;
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
