package entities;

import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Entity{

    private int healthPoints;
    private int positionX;
    private int positionY;

    private Node node;
    private ImageView iv;
    private Sprite sprite;
    private Sprite spriteDefault;

    public Entity() {}

//    public Entity(Node node, int x, int y) {
//        this.node = node;
//        this.positionX = x;
//        this.positionY = y;
//        this.node.setTranslateX(x);
//        this.node.setTranslateY(y);
//    }

    /*public Entity(String filename, int x, int y) {
        this.iv = new ImageView();
        //this.sprite = new Sprite(filename, x, y);
        //this.node = new Circle(this.sprite.getWidth()/2, this.sprite.getHeight()/2, this.sprite.getHeight()/2, Color.RED);
        this.positionX = x;
        this.positionY = y;
        this.node.setTranslateX(x);
        this.node.setTranslateY(y);
    }*/

    public Entity(String filename, String extension, int numberImages, int positionX, int positionY, int healthPoints) {
        this.iv = new ImageView();
        this.spriteDefault = new Sprite(this.iv, filename, extension, numberImages);
        this.sprite = this.spriteDefault;
        this.node = new Circle(this.sprite.getWidth()/2, this.sprite.getHeight()/2, 2*this.sprite.getHeight()/5, Color.BLUE);
        this.positionX = positionX;
        this.positionY = positionY;
        this.node.setTranslateX(positionX);
        this.node.setTranslateY(positionY);
        this.healthPoints = healthPoints;
    }

    /***
     * Method for checking for collision between two objects of type Node
     * @param otherEntity
     * @return
     */
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

    public ImageView getIv() {
        return this.iv;
    }

    public void setIv(ImageView iv) {
        this.iv = iv;
    }
}
