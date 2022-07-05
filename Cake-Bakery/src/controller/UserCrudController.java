package controller;

import db.DBConnection;
import model.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserCrudController {
    public static boolean signupUser(user us) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO User VALUES (?,?,?)");
        stm.setObject(1, us.getUserId());
        stm.setObject(2, us.getUserName());
        stm.setObject(3, us.getPassword());

        return stm.executeUpdate() > 0;
    }

}
