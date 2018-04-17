package menuOptions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsMenu implements Initializable {

    Stage window_GameOptions, window_VideoOptions, window_SoundOptions, window_Controls,window_GameMenu;
    Scene scene_GameOptions, scene_VideoOptions, scene_SoundOptions, scene_Controls, scene_GameMenu;
    Parent root_GameOptions, root_videoOptions, root_SoundOptions, root_Controls, root_GameMenu;

    @FXML
    Button GameOptions, VideoOptions, SoundOptions, Controls, back_Options;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    public void GameOptions() {
        try {
            root_GameOptions = FXMLLoader.load(getClass().getResource("gameOptions.fxml"));
            scene_GameOptions = new Scene(root_GameOptions, 500, 500);
        } catch (Exception e) {
            System.out.println("Error: \n" + e.getMessage());
        }
        window_GameOptions = new Stage();
        window_GameOptions.setScene(scene_GameOptions);
        window_GameOptions.show();
    }

    @FXML
    public void VideoOptions() {
        try {
            root_videoOptions = FXMLLoader.load(getClass().getResource("videoOptions.fxml"));
            scene_VideoOptions = new Scene(root_videoOptions, 500, 500);
        } catch (Exception e) {
            System.out.println("Error: \n" + e.getMessage());
        }
        window_VideoOptions = new Stage();
        window_VideoOptions.setScene(scene_VideoOptions);
        window_VideoOptions.show();
    }

    @FXML
    public void SoundOptions() {
        try {
            root_SoundOptions = FXMLLoader.load(getClass().getResource("soundOptions.fxml"));
            scene_SoundOptions = new Scene(root_SoundOptions, 500, 500);
        } catch (Exception e) {
            System.out.println("Error: \n" + e.getMessage());
        }
        window_SoundOptions = new Stage();
        window_SoundOptions.setScene(scene_VideoOptions);
        window_SoundOptions.show();
    }

    @FXML
    public void Controls() {
        try {
            root_Controls = FXMLLoader.load(getClass().getResource("controls.fxml"));
            scene_Controls = new Scene(root_Controls, 500, 500);
        } catch (Exception e) {
            System.out.println("Error: \n" + e.getMessage());
        }
        window_Controls = new Stage();
        window_Controls.setScene(scene_Controls);
        window_Controls.show();
    }

    @FXML
    public void BackToGameMenu(ActionEvent event) {
        try {
            if (event.getSource() == back_Options){
                window_GameMenu = (Stage) back_Options.getScene().getWindow();
                root_GameMenu = FXMLLoader.load(getClass().getResource("../main/GameMenu.fxml"));
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        scene_GameMenu = new Scene(root_GameMenu, 1280, 720);
        window_GameMenu.setScene(scene_GameMenu);
        window_GameMenu.show();
    }

}

