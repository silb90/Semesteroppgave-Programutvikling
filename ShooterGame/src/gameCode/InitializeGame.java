package gameCode;

import entities.Enemy;
import entities.Player;
import entities.Zombie;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.MainController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InitializeGame implements Initializable{

    @FXML Pane gameWindow;
    @FXML MenuBar topbar;

    Stage stage = new Stage();

    private Player player;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private Game game;
    private boolean paused = false;
    private SceneSizeChangeListener sceneChange;

    final private boolean DEBUG = false;

    public void exit(){
        System.out.println("hello");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Create all Entity objects
        try {
            player = new Player("/resources/Top_Down_Survivor/knife/idle/survivor-idle_knife_", ".png", 20, 500, 500, 100);
            player.knifeAnimation();
            player.setSpriteSize(250,250);
        } catch (Exception e) {
            System.out.println("Error: Player did not load correctly");
        }

        try {
            for (int i = 0; i < 5; i++) {
                enemyList.add(new Zombie("/resources/Zombie/skeleton-idle_", ".png", 17, (int) (Math.random() * 1280), (int) (Math.random() * 720), 100));
                enemyList.get(i).setSpriteIdle("/resources/Zombie/skeleton-idle_", ".png", 17);
                enemyList.get(i).setSpriteMoving("/resources/Zombie/skeleton-move_", ".png", 17);
                enemyList.get(i).setSpriteMelee("/resources/Zombie/skeleton-attack_", ".png", 9);
            }
        } catch (Exception e) {
            System.out.println("Error: Enemies did not load correctly");
        }

        // Add Entities to the gameWindow
        // Enable DEBUG in order to view the Entities represented as Nodes (E.g. if Sprites fail to load correctly)
        if(DEBUG)
            gameWindow.getChildren().add(player.getNode());

        gameWindow.getChildren().add(player.getSprite().getImageView());

        for (Enemy enemy : enemyList) {
            if(DEBUG)
                gameWindow.getChildren().add(enemy.getNode());

            gameWindow.getChildren().add(enemy.getSprite().getImageView());
        }

        // Initialize the game
        game = new Game(player, enemyList, gameWindow);

        Platform.runLater(this::getKeyPressed);

        sceneChange = new SceneSizeChangeListener(stage.getScene(), 1.6, 1280, 720, gameWindow);

    }

    /***
     * Method for handling player input via keyboard
     */
    public void getKeyPressed(){

        gameWindow.getScene().setOnKeyPressed(e -> {
            player.movePlayer(e);
            Stage stage = (Stage) gameWindow.getScene().getWindow();
            if (e.getCode() == KeyCode.F12) {
                if (stage.isFullScreen()) {
                    stage.setFullScreen(false);
                    topbar.setVisible(true);
                }
                else {
                    stage.setFullScreen(true);
                    topbar.setVisible(false);
                }
            } else if (e.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            } else if (e.getCode() == KeyCode.P) {
                if(!paused) {
                    game.pauseGame();
                    paused = true;
                }
                else {
                    game.resumeGame();
                    paused = false;
                }
            } else if (e.getCode() == KeyCode.F) {
                player.knifeAnimation();
            }
        });
        gameWindow.getScene().setOnKeyReleased(e -> {
            player.releasedPlayer(e);
        });
    }

    public void changeFullscreen() {
        Stage stage = (Stage) gameWindow.getScene().getWindow();
        if (stage.isFullScreen())
            stage.setFullScreen(false);
        else
            stage.setFullScreen(true);
    }

    public void exitGame() {
        System.exit(0);
    }
}
