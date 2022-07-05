package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import util.CrudUtil;
import view.tm.ShowOrderDetailTM;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadAllOrderDetailsFormController {
    public AnchorPane LoadOrderDetailFormContext;
    public TableView tblLoadAllOrder;
    public TableColumn colOrderId;
    public TableColumn colCustomerId;
    public TableColumn colDeliverId;
    public TableColumn colPayId;
    public TableColumn colOrderDate;
    public TableColumn colTotalCost;


    public void initialize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory("customerId"));
        colDeliverId.setCellValueFactory(new PropertyValueFactory("deliverId"));
        colPayId.setCellValueFactory(new PropertyValueFactory("payId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory("totalCost"));
        try {
            loadAllOrders();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllOrders() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM `Order`");
        ObservableList<ShowOrderDetailTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new ShowOrderDetailTM(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getDouble(6)
                    )
            );
        }
        tblLoadAllOrder.setItems(obList);

    }
}
