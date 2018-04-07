package entities;

import gameCode.InitializeGame;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Entity{

    private int healthPoints;
    private int bonus;
    private int positionX;
    private int positionY;
    private int score = 0;

    private Node node;
    private Sprite sprite;

    Rectangle removeRect = null;

    public Entity() {}

    public Entity(Node node, int x, int y) {
        this.node = node;
        this.positionX = x;
        this.positionY = y;
        this.node.setTranslateX(x);
        this.node.setTranslateY(y);
    }

    public Entity(String filename, int x, int y) {
        this.sprite = new Sprite(filename, x, y);
        this.node = new Circle(this.sprite.getWidth()/2, this.sprite.getHeight()/2, this.sprite.getHeight()/2, Color.RED);
        this.positionX = x;
        this.positionY = y;
        this.node.setTranslateX(x);
        this.node.setTranslateY(y);
    }

    public Entity(String filename, String extension, int numberImages, int positionX, int positionY, int healthPoints, int bonus) {
        this.sprite = new Sprite(filename, extension, numberImages);
        this.node = new Circle(this.sprite.getWidth()/2, this.sprite.getHeight()/2, 2*this.sprite.getHeight()/5, Color.BLUE);
        this.positionX = positionX;
        this.positionY = positionY;
        this.node.setTranslateX(positionX);
        this.node.setTranslateY(positionY);
        this.healthPoints = healthPoints;
        this.bonus = bonus;

    }

    public boolean isColliding(Entity otherEntity) {
        return this.node.getBoundsInParent().intersects(otherEntity.getNode().getBoundsInParent());
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int health) {
        this.healthPoints = health;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }


}
