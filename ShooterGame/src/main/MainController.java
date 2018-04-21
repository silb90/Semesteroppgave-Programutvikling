package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    private Stage loadWindow, optionWindow, helpWindow;
    private Scene loadScene, optionsScene, helpScene;
    private Parent loadRoot, optionsRoot, helpRoot;

    @FXML
    Button newGame, loadGame, options, help, exit;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void launchGame(){
        try{
            Stage stage = (Stage) newGame.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../gameCode/GameWindow.fxml"));/* Exception */
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("../menuOptions/MenuStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        }catch (IOException io){
            io.printStackTrace();
        }

    }

    public void openLoadMenu(ActionEvent event) throws IOException{

        try {
            if(event.getSource() == loadGame) {
                loadWindow = (Stage) loadGame.getScene().getWindow();
                loadRoot = FXMLLoader.load(getClass().getResource("../menuOptions/LoadMenu.fxml"));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        loadScene = new Scene(loadRoot, 1280, 720);
        loadWindow.setScene(loadScene);
        loadWindow.show();
    }

    public void openSettings(ActionEvent event) throws IOException{

        try {
            if (event.getSource() == options){
                optionWindow = (Stage) options.getScene().getWindow();
                optionsRoot = FXMLLoader.load(getClass().getResource("../menuOptions/Settings.fxml"));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        optionsScene = new Scene(optionsRoot, 1280, 720);
        optionWindow.setScene(optionsScene);
        optionWindow.show();
    }

    public void openHowToPlay(ActionEvent event) throws IOException{

        try {
            if (event.getSource() == help){
                helpWindow = (Stage) help.getScene().getWindow();
                helpRoot = FXMLLoader.load(getClass().getResource("../menuOptions/HowToPlay.fxml"));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        helpScene = new Scene(helpRoot, 1280, 720);
        helpWindow.setScene(helpScene);
        helpWindow.show();
    }

    public void exitGame(){

        Stage stage = (Stage)
                exit.getScene().getWindow();
        stage.close();
    }
}