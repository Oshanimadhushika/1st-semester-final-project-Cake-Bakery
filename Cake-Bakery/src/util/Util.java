package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Util {
    public static void navigate(AnchorPane anchorPane, String location) throws IOException, IOException {

        anchorPane.getChildren().clear();
        Parent parent = FXMLLoader.load(Util.class.getResource("../view/"+location+".fxml"));
        anchorPane.getChildren().add(parent);

    }
}
