package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;
import view.tm.paymentTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentFormController {
    public AnchorPane PaymentFormContext;
    public TableView tblPayment;
    public TableColumn tblcolPaymentId;
    public TableColumn tblcolPaymentDetail;
    public TableColumn tblColPaymentDate;
    public TableColumn tblPayMeth;
    public TableColumn tblColumnOption;
    public TableColumn tblcolPayMeth;
    public TableColumn colOption;


    public void initialize(){
        tblcolPaymentId.setCellValueFactory(new PropertyValueFactory("payId"));
        tblcolPaymentDetail.setCellValueFactory(new PropertyValueFactory("payDetail"));
        tblcolPayMeth.setCellValueFactory(new PropertyValueFactory("payMethod"));


        try {
            loadAllPayments();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllPayments() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Payment");
        ObservableList<paymentTM> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new paymentTM(
                            result.getString("payId"),
                            result.getString("payDetail"),
                            result.getString("payMethod")

                    )
            );
        }
        tblPayment.setItems(obList);

    }

}
