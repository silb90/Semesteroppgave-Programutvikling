package entities;

import gameCode.DataHandler;
import gameCode.Game;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Arc;
import javafx.scene.transform.Rotate;

import java.util.*;

public class Zombie extends Movable {

    private final int HUNTDISTANCE = 750;
    private final int ATTACKDISTANCE = 70;

    private Direction walkDirection;
    private State state;
    private int walkDistance;
    private Queue<AnimationLengthPair> animationQueue;
    private long waitTime;
    private Image[] placeHolder;
    private List<Bullet> attackList;

    public Zombie(Image[][] images, AudioClip[] audioClips, int positionX, int positionY, int healthPoints) {
        super(new AnimationHandler(images), audioClips, positionX, positionY, healthPoints, 1.0);
        this.animationQueue = new LinkedList<AnimationLengthPair>();
        this.waitTime = 0;
        this.state = State.NORMAL;
        this.walkDirection = Direction.IDLE;
        this.placeHolder = images[0];
        this.attackList = new ArrayList<Bullet>();
    }

    public void removeImage(boolean isAlive, Game game, List<Drop> drops) {
        if (!isAlive) {
            if(game.getGameInitializer().isDEBUG())
                game.getGameWindow().getChildren().remove(getNode());
            game.getGameWindow().getChildren().remove(getAnimationHandler().getImageView());

            game.scorePerKill();
            drops.add(game.getRandomDropType(this));
        }
    }

    /***
     * Method which controls the walk direction of enemies, whereas they are drawn towards the Player.
     * @param player Requires an object of type Player in order to decide which Entity the enemies
     *               should pursue.
     */
    public void findDirection(Player player) {
        double diffx = (player.getPositionX()) - getPositionX();
        double diffy = (player.getPositionY()) - getPositionY();
        double angle = 180 + Math.atan2(diffy, diffx) * (180 / Math.PI);
        double distance = Math.sqrt(Math.pow(diffx, 2) + Math.pow(diffy, 2));

        if (distance < HUNTDISTANCE && distance >= ATTACKDISTANCE && this.walkDistance <= 0) {
            if (angle > 340 || angle <= 25) {
                this.walkDirection = Direction.WEST;
                this.walkDistance = 10;
            } else if (angle > 25 && angle <= 70) {
                this.walkDirection = Direction.NORTHWEST;
                this.walkDistance = 10;
            } else if (angle > 70 && angle <= 115) {
                this.walkDirection = Direction.NORTH;
                this.walkDistance = 10;
            } else if (angle > 115 && angle <= 160) {
                this.walkDirection = Direction.NORTHEAST;
                this.walkDistance = 10;
            } else if (angle > 160 && angle <= 205) {
                this.walkDirection = Direction.EAST;
                this.walkDistance = 10;
            } else if (angle > 205 && angle <= 250) {
                this.walkDirection = Direction.SOUTHEAST;
                this.walkDistance = 10;
            } else if (angle > 250 && angle <= 295) {
                this.walkDirection = Direction.SOUTH;
                this.walkDistance = 10;
            } else if (angle > 295 && angle <= 340) {
                this.walkDirection = Direction.SOUTHWEST;
                this.walkDistance = 10;
            }
        }

        if (distance <= ATTACKDISTANCE && this.walkDistance <= 0) {
            this.state = State.ATTACK;
            this.walkDistance = 10;
        } else {
            this.state = State.NORMAL;
        }

        if (this.walkDistance <= 0) {
            this.walkDirection = Direction.IDLE;
            this.walkDistance = 0;
        }
    }

    public void move(int difficultyModifier) {
        int action;
        int animationLength = 0;
        switch (this.walkDirection) {
            case IDLE:
                stopX();
                stopY();
                this.walkDistance = 0;
                action = 0;
                break;
            case NORTH:
                goUp();
                stopX();
                this.walkDistance--;
                action = 1;
                break;
            case NORTHEAST:
                goUp();
                goRight();;
                this.walkDistance--;
                action = 1;
                break;
            case EAST:
                goRight();
                stopY();
                this.walkDistance--;
                action = 1;
                break;
            case SOUTHEAST:
                goDown();
                goRight();
                this.walkDistance--;
                action = 1;
                break;
            case SOUTH:
                goDown();
                stopX();
                this.walkDistance--;
                action = 1;
                break;
            case SOUTHWEST:
                goDown();
                goLeft();
                this.walkDistance--;
                action = 1;
                break;
            case WEST:
                goLeft();
                stopY();
                this.walkDistance--;
                action = 1;
                break;
            case NORTHWEST:
                goUp();
                goLeft();
                this.walkDistance--;
                action = 1;
                break;
            default:
                stopX();
                stopY();
                this.walkDistance = 0;
                action = 0;
        }

        switch (state) {
            case NORMAL:
                break;
            case ATTACK:
                stopX();
                stopY();
                action = 2;
                animationLength = 500;
                attack(difficultyModifier);
                this.walkDistance = 0;
                break;
            case DAMAGED:
                break;
        }
        setAnimation(action, animationLength);
    }

    public void attack(int difficultyModifier) {
        long currentTime = System.currentTimeMillis();
        if (currentTime > this.waitTime) {
            int posX = getPositionX();
            int posY = getPositionY();
            switch (this.getDirection()) {
                case EAST:
                    posX += 2 * this.getAnimationHandler().getImageView().getImage().getWidth() / 3;
                    posY += this.getAnimationHandler().getImageView().getImage().getHeight() / 2;
                    break;
                case NORTHEAST:
                    posX += 2 * this.getAnimationHandler().getImageView().getImage().getWidth() / 3;
                    posY += this.getAnimationHandler().getImageView().getImage().getHeight() / 2;
                    break;
                case WEST:
                    posX += this.getAnimationHandler().getImageView().getImage().getWidth() / 3;
                    posY += 2 * this.getAnimationHandler().getImageView().getImage().getWidth() / 3;
                    break;
                case NORTHWEST:
                    posX += 2 * this.getAnimationHandler().getImageView().getImage().getWidth() / 3;
                    posY += 2 * this.getAnimationHandler().getImageView().getImage().getHeight() / 3;
                    break;
                case NORTH:
                    posX += 2 * this.getAnimationHandler().getImageView().getImage().getWidth() / 3;
                    posY += this.getAnimationHandler().getImageView().getImage().getHeight() / 2;
                    break;
                case SOUTH:
                    posX += 2 * this.getAnimationHandler().getImageView().getImage().getWidth() / 3;
                    posY += this.getAnimationHandler().getImageView().getImage().getHeight() / 2;
                    break;
                case SOUTHEAST:
                    posX += 2 * this.getAnimationHandler().getImageView().getImage().getWidth() / 3;
                    posY += this.getAnimationHandler().getImageView().getImage().getHeight() / 2;
                    break;
                case SOUTHWEST:
                    posX += 2 * this.getAnimationHandler().getImageView().getImage().getWidth() / 3;
                    posY += this.getAnimationHandler().getImageView().getImage().getHeight() / 2;
                    break;
                case IDLE:
                    break;
            }

            int fireRate = 500;
            int damage = 20 * difficultyModifier - 10 * (difficultyModifier - 1);
            Bullet zombieSlash = new Bullet(placeHolder, posX, posY, 0, damage, this.getDirection(), 0);
            //zombieSlash.setDrawn();
            zombieSlash.setNewRotation(getNewRotation());
            Arc knifeArc = new Arc(0, 0, 25, 30.0, 90, 180);
            knifeArc.setTranslateX(posX);
            knifeArc.setTranslateY(posY);
            knifeArc.getTransforms().add(new Rotate(180, 0, 0));
            zombieSlash.setNode(knifeArc);
            attackList.add(zombieSlash);
            this.waitTime = currentTime + fireRate;
        }
    }

    /**
     *
     * @param animationAction
     */
    private void setAnimation(int animationAction, int animationLength) {
//        double duration = 0.064;
//        if(animationAction == 2) {
//            duration = 0.064;
//        }
        boolean not = true;
        for (AnimationLengthPair pair : animationQueue) {
            if (pair.animationAction == animationAction)
                not = false;
        }
        if (not)
            animationQueue.add(new AnimationLengthPair(0, animationAction, animationLength, 0.064));
    }

    /**
     *
     */
    public void updateAnimation() {
        long currentTime = System.currentTimeMillis();
        AnimationLengthPair pair = animationQueue.peek();
        if (pair != null) {
            if (currentTime > this.waitTime) {
                getAnimationHandler().setImageAction(animationQueue.peek().animationAction);
                this.waitTime = currentTime + animationQueue.peek().time;
                getAnimationHandler().setDuration(animationQueue.peek().duration);
                animationQueue.remove();
            }
        }
    }

    /**
     * Method which will retrieve and return requested information about a Zombie object.
     * In this instance, Zombie shares the same relevant variables as the super class, Movable.
     * @return Returns an object of type MovementConfiguration set by a super method call.
     */
    public DataHandler.MovementConfiguration getZombieConfiguration() {
        return super.getMovementConfiguration();
    }

    /**
     * Method which will transfer provided zombieCfg's variables into corresponding variables in Zombie.
     * Variables are inherited from Movable, and are thus transferred and set through a super method call.
     * @param zombieCfg Requires an object of type MovementConfiguration.
     */
    public void setZombieConfiguration(DataHandler.MovementConfiguration zombieCfg) {
        super.setMovementConfiguration(zombieCfg);
    }

    public List<Bullet> getAttackList() {
        return attackList;
    }

    public State getState() {
        return state;
    }
}