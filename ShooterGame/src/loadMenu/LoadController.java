package loadMenu;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadController implements Initializable {

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    @FXML
    Button saveFile1;



    public void loadFile1() {

        System.out.println("loading file 1");

    }

}
