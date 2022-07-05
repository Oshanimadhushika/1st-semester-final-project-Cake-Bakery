package controller;

import model.rider;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliverCrudController {
    public static ArrayList<String> getRiderIDs() throws SQLException, ClassNotFoundException {

        ResultSet result= CrudUtil.execute("SELECT deliverId FROM Deliver");
        ArrayList<String> ids=new ArrayList<>();

        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static rider getRider(String id) throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT * FROM Deliver WHERE deliverId=?",id);

        if (result.next()){
            return new rider(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return  null;

    }
}
