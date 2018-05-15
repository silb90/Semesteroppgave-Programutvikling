package entities;

import javafx.scene.image.Image;

/**
 * Class which represents a Rock, with the same qualities as an Entity.
 */
public class Rock extends Entity {
    public Rock(Image[] images, int positionX, int positionY) {
        super(new AnimationHandler(images), positionX, positionY);
        this.getAnimationHandler().getImageView().setTranslateX(this.getNode().getTranslateX());
        this.getAnimationHandler().getImageView().setTranslateY(this.getNode().getTranslateY());
    }
}