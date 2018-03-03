package GameCode;

import Entities.Enemy;
import Entities.Player;
import Entities.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    Player player;
    List<Enemy> enemyList = new ArrayList<Enemy>();

    private void onUpdate() {
        /*player.update(enemyList);

        for (Enemy enemy : enemyList)
            if (player.isColliding(enemy))
                System.out.println("Collision!");*/
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //player = new Player(new Rectangle(100,100, Color.BLUE), 500, 500);
        player = new Player(getClass().getResource("/resources/test_sprites.png").toURI().toString(), 500, 500);
        enemyList.add(new Enemy(new Circle(25,25,50,Color.RED), (int)(Math.random() * 500), (int)(Math.random() * 500)));
        enemyList.add(new Enemy(new Circle(25,25,50,Color.RED), (int)(Math.random() * 500), (int)(Math.random() * 500)));
        enemyList.add(new Enemy(new Circle(25,25,50,Color.RED), (int)(Math.random() * 500), (int)(Math.random() * 500)));

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Topdown Shooter");

        Pane pane = new Pane();
        pane.setPrefSize(1280, 720);

        ImageView iv = new ImageView();
        iv.setImage(player.getSprite().getSprite());
        iv.relocate(640, 360);

        pane.getChildren().add(iv);
        for (Enemy enemy : enemyList)
            pane.getChildren().add(enemy.getNode());

        primaryStage.setScene(new Scene(pane));

        primaryStage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                player.goLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                player.goRight();
            } else if (e.getCode() == KeyCode.UP) {
                player.goUp();
            } else if (e.getCode() == KeyCode.DOWN) {
                player.goDown();
            }
        });

        primaryStage.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                player.stopX();
            } else if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                player.stopY();
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };

        timer.start();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}