package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.CrudUtil;
import util.Util;
import view.tm.cartTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrderFormController {
    public AnchorPane OrderFormContext;
    public TextField txtOrderId;
    public TextField txtDate;
    public ComboBox cmbCusId;
    public TextField txtCusName;
    public TextField txtAddress;
    public TextField txtDescription;
    public TextField txtQty;
    public ComboBox cmbItemId;
    public TextField txtPrice;
    public ComboBox cmbPayMeth;
    public TableView<cartTM> tblOrederForm;
    public TableColumn colOrderId;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQTy;
    public TableColumn colDate;
    public TextField txtTotalPrice;
    public TextField txtPaymentId;
    public TextField txtPaymentDetail;
    public ComboBox cmbDeliverId;
    public TextField txtDeliverName;
    public TextField txtVehiNo;
    public TableColumn colCostTotal;
    public TextField txtContactNo;
    public TextField txtCusContactNo;
    public TextField txtdeliverContactNo;
    public TextField txtTime;
    public TextField txtCost;
    public TableColumn colTotalCost;
    public TableColumn colOption;
    public TextField txtQtyOnHand;
    public TextField txtGoToCustomer;
    public TextField txtGoToItem;
    public TextField txtGoToDeliver;
    public TextField lblTotalPrice;
    String date;

    public void initialize() throws SQLException, ClassNotFoundException {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQTy.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn1"));

        ObservableList<String> obl = FXCollections.observableArrayList();

        obl.add("Cash On Delivery");
        obl.add("Bank Transfer");

        cmbPayMeth.setItems(obl);

        loadDate();
        setCustomerIds();
        setItemIds();
        setRiderIds();
        txtOrderId.setText(generateOrderId());
        txtPaymentId.setText(generatePaymentId());

       /* tblOrederForm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                try {
                    customerTM selectedItem = (orderTM) tblOrederForm.getSelectionModel().getSelectedItem();
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
        });*/

        cmbCusId.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setCustomerDetails((String) newValue);
                });

        cmbItemId.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setItemDetails((String) newValue);
                });

        cmbDeliverId.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setRiderDetails((String) newValue);
                });


    }

    private void setRiderDetails(String selectedDeliverId) {
        try {
            rider d = DeliverCrudController.getRider(selectedDeliverId);
            if (d != null) {
                txtDeliverName.setText(d.getDeliverName());
                txtdeliverContactNo.setText(d.getContactNo());
                txtVehiNo.setText(d.getVehiNo());

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemDetails(String selectedItemId) {
        try {
            item i = itemcrudController.getItem(selectedItemId);
            if (i != null) {
                txtDescription.setText(i.getDescription());
                txtPrice.setText(String.valueOf(i.getUnitPrice()));
                txtQtyOnHand.setText(String.valueOf(i.getQtyOnHand()));

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void setCustomerDetails(String selectedCustomerId) {
        try {
            customer c = customerCrudController.getCustomer(selectedCustomerId);
            if (c != null) {
                txtCusName.setText(c.getCusName());
                txtAddress.setText(c.getAddress());
                txtCusContactNo.setText(c.getContactNo());

            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setCustomerIds() {
        try {
            ObservableList<String> cusidOblist = FXCollections.observableArrayList(
                    customerCrudController.getCusIDs()
            );
            cmbCusId.setItems(cusidOblist);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setItemIds() {
        try {
            ObservableList<String> itemidOblist = FXCollections.observableArrayList(
                    itemcrudController.getItemIDs()
            );
            cmbItemId.setItems(itemidOblist);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setRiderIds() {
        try {
            ObservableList<String> deliveridOblist = FXCollections.observableArrayList(
                    DeliverCrudController.getRiderIDs()
            );
            cmbDeliverId.setItems(deliveridOblist);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ObservableList<cartTM> tmList = FXCollections.observableArrayList();

    public void AddToCartOnAction(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double totalPrice = unitPrice * qty;

        Button btn1 = new Button("Delete");

        cartTM od = new cartTM(
                txtOrderId.getText(),
                String.valueOf(cmbItemId.getValue()),
                txtDescription.getText(),
                unitPrice,
                qty,
                totalPrice,
                btn1

        );

        try {
            updateQuentity((String) cmbItemId.getValue(), Integer.parseInt(txtQty.getText()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        int rowNum = isExists(od);
        if (rowNum == -1) {
            btn1.setOnAction(e -> {
                tmList.remove(od);
                new Alert(Alert.AlertType.CONFIRMATION, "Deleted!..").show();

                calculateTotal();
                clearAllTexts();
            });
            tmList.add(od);
        } else {
            cartTM temp = tmList.get(rowNum);
            cartTM newTm = new cartTM(temp.getOrderId(), temp.getItemId(), temp.getDescription(), temp.getUnitPrice(), Integer.valueOf((temp.getQty()) + Integer.parseInt(txtQty.getText())), temp.getTotalCost(), temp.getBtn1());
            tmList.remove(rowNum);
            tmList.add(newTm);
        }
        //tblOrder.setItems(tmList);

        //tmList.add(od);
        tblOrederForm.setItems(tmList);
        calculateTotal();
       // clearAllTexts();
    }

    private void clearAllTexts() {
        cmbCusId.requestFocus();
        cmbItemId.requestFocus();
        cmbDeliverId.requestFocus();
        txtCusName.clear();
        txtAddress.clear();
        txtCusContactNo.clear();
        txtDescription.clear();
        txtPrice.clear();
        txtQtyOnHand.clear();
        txtQty.clear();
        txtDeliverName.clear();
        txtdeliverContactNo.clear();
        txtVehiNo.clear();
    }

    private void calculateTotal() {
        double total = 0;
        for (cartTM tm : tmList
        ) {
            total += tm.getTotalCost();
        }
        lblTotalPrice.setText(String.valueOf(total));
    }


    private Boolean updateQuentity(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection()
                .prepareStatement
                        ("UPDATE Item SET qtyOnHand=(qtyOnHand-" + qty
                                + ") WHERE itemId='" + itemCode + "'");
        return stm.executeUpdate() > 0;
    }

    private int isExists(cartTM tm) {
        for (int i = 0; i < tmList.size(); i++) {
            if (tm.getItemId().equals(tmList.get(i).getItemId())) {
                return i;
            }
        }
        return -1;
    }



    public void PlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<orderDetail> items = new ArrayList<>();
        for (cartTM temp : tmList
        ) {
            items.add(new orderDetail(txtOrderId.getText(), temp.getItemId(),Integer.parseInt( txtQty.getText()), Double.valueOf(temp.getUnitPrice())));
        }
        order or = new order(txtOrderId.getText(),(String) cmbCusId.getValue(), (String) cmbDeliverId.getValue(),txtPaymentId.getText(),txtDate.getText(),Double.parseDouble(lblTotalPrice.getText()), items);
        if (placeOrder(or)){
            new Alert(Alert.AlertType.CONFIRMATION,"Your Order is Succesfull!").show();
            //txtOrderId.setText(generateOrderId());
            //clearAllTexts();

            try {
                updateQuentity((String) cmbItemId.getValue(),Integer.parseInt(txtQty.getText()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            new Alert(Alert.AlertType.WARNING,"Your Order is Failed!").show();
        }
    }

    private String generateOrderId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "OR-00" + tempId;
            } else if (tempId < 99) {

                return "OR-0" + tempId;

            } else {
                return "OR-" + tempId;
            }

        } else {
            return "OR-001";
        }
    }

    private String generatePaymentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DBConnection.getInstance()
                .getConnection().prepareStatement(
                        "SELECT payId FROM Payment ORDER BY payId DESC LIMIT 1"
                ).executeQuery();
        if (rst.next()) {

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId = tempId + 1;
            if (tempId <= 9) {
                return "P-00" + tempId;
            } else if (tempId < 99) {

                return "P-0" + tempId;

            } else {
                return "P-" + tempId;
            }

        } else {
            return "P-001";
        }
    }

    private boolean placeOrder(order od) {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("INSERT INTO `Order` VALUES(?,?,?,?,?,?) ");
            stm.setObject(1, od.getOrderId());
            stm.setObject(2, od.getcId());
            stm.setObject(3, od.getdId());
            stm.setObject(4, od.getpId());
            stm.setObject(5, od.getOrderDate());
            stm.setObject(6, od.getTotalCost());

            if (stm.executeUpdate() > 0) {
                if (saveOrderDetails(od.getOrderId(), od.getItems())) {
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

    private boolean saveOrderDetails(String orderId, ArrayList<orderDetail> items) throws SQLException, ClassNotFoundException {
        for (orderDetail temp:items
        ) {
            if (CrudUtil.execute("INSERT INTO Order_Detail VALUES(?,?,?,?)",orderId,temp.getItemId(),temp.getQty(),temp.getUnitPrice())){

            }else {
                return false;
            }
        }
        return true;
    }

    public void PrintBillOnAction(ActionEvent actionEvent) {
        String orderId = txtOrderId.getText();
        double total = Double.parseDouble(lblTotalPrice.getText());

        HashMap paramMap = new HashMap();
        paramMap.put("orderid", orderId);// id = report param name // customerID = UI typed value
        paramMap.put("total", total);



        try {
            JasperReport compileReport  = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/BillReport.jasper"));
            ObservableList<cartTM> tableRecords = tblOrederForm.getItems();
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,paramMap,new JRBeanArrayDataSource(tblOrederForm.getItems().toArray()));
            JasperViewer.viewReport(jasperPrint,false);
            clearAllTexts();


        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void PayAddOnAction(ActionEvent actionEvent) {
        try {
            if (CrudUtil.execute("INSERT INTO Payment VALUES (?,?,?)", txtPaymentId.getText(), txtPaymentDetail.getText(), cmbPayMeth.getValue())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved!..").show();
               // clearAllPaymentTexts();

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public void PayRemoveOnAction(ActionEvent actionEvent) {
        try {

            if (CrudUtil.execute("DELETE FROM Payment WHERE payId=?", txtPaymentId.getText())) {
                new Alert(Alert.AlertType.CONFIRMATION, "Removed!!").show();
                clearAllPaymentTexts();
                //loadAllCustomers();
                // btnSaveCustomer.setText("Save Customer");
            } else {
                new Alert(Alert.AlertType.WARNING, "Try Again!").show();
            }

        } catch (SQLException | ClassNotFoundException e) {

        }
    }

    private void clearAllPaymentTexts() {
        txtPaymentId.clear();
        txtPaymentDetail.clear();
        cmbPayMeth.requestFocus();
        txtPaymentId.requestFocus();


    }

    private void loadDate() {
        date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
        txtDate.setText(new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
    }

    public void paymentIdOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws ClassNotFoundException, SQLException {
        ResultSet result = (ResultSet) CrudUtil.execute("SELECT * FROM Payment WHERE payId=?", txtPaymentId.getText());
        if (result.next()) {
            txtPaymentDetail.setText(result.getString(2));
            cmbPayMeth.setValue(result.getString(3));


        } else {
            new Alert(Alert.AlertType.WARNING, "Empty Result").show();
        }
    }

    public void ViewOrderOnAction(ActionEvent actionEvent) throws IOException {

        Util.navigate(OrderFormContext,"LoadAllOrderDetailsForm");
    }
}

