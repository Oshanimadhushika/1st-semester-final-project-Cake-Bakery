package controller;

import model.order;
import model.supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class orderCrudController {
    public static ArrayList<String> getSupplierIDs() throws SQLException, ClassNotFoundException {

        ResultSet result = CrudUtil.execute("SELECT orderId FROM `Order`");
        ArrayList<String> ids = new ArrayList<>();

        while (result.next()) {
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static order getOrder(String id) throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT * FROM `Order` WHERE orderId=?",id);

        if (result.next()){
            return new order(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getDouble(6)
            );
        }
        return  null;

    }
}