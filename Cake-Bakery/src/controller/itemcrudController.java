package controller;

import model.customer;
import model.item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class itemcrudController {
    public static ArrayList<String> getItemIDs() throws SQLException, ClassNotFoundException {

      ResultSet result= CrudUtil.execute("SELECT itemId FROM Item");
      ArrayList<String> ids=new ArrayList<>();

      while (result.next()){
          ids.add(result.getString(1));
      }
      return ids;
    }

    public static item getItem(String id) throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT * FROM Item WHERE itemId=?",id);

        if (result.next()){
            return new item(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getDouble(4),
                    result.getInt(5)
            );
        }
        return  null;

    }
}
