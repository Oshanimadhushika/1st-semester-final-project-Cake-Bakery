package controller;

import model.supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class supplierCrudController {
    public static ArrayList<String> getSupplierIDs() throws SQLException, ClassNotFoundException {

        ResultSet result= CrudUtil.execute("SELECT supplierCode FROM Supplier");
        ArrayList<String> ids=new ArrayList<>();

        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static supplier getSupplier(String id) throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT * FROM Supplier WHERE supplierCode=?",id);

        if (result.next()){
            return new supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return  null;

    }
}
