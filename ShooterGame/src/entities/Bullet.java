package entities;

import java.util.List;

public class Bullet extends Movable {

    private int damage;
    private Direction direction;

    public Bullet(String filename, int positionX, int positionY, double movementSpeed, int damage, Direction direction, List<Rock> rocks) {
        super(filename, positionX, positionY, movementSpeed, rocks);
        this.damage = damage;
        this.direction = direction;
    }

    public void bulletDirection() {
        switch(this.direction) {
            case NORTH:
                setVelocityX(0);
                setVelocityY(-getMovementSpeed());
                break;
            case NORTHEAST:
                setVelocityX(getMovementSpeed());
                setVelocityY(-getMovementSpeed());
                break;
            case EAST:
                setVelocityX(getMovementSpeed());
                setVelocityY(0);
                break;
            case SOUTHEAST:
                setVelocityX(getMovementSpeed());
                setVelocityY(getMovementSpeed());
                break;
            case SOUTH:
                setVelocityX(0);
                setVelocityY(getMovementSpeed());
                break;
            case SOUTHWEST:
                setVelocityX(-getMovementSpeed());
                setVelocityY(getMovementSpeed());
                break;
            case WEST:
                setVelocityX(-getMovementSpeed());
                setVelocityY(0);
                break;
            case NORTHWEST:
                setVelocityX(-getMovementSpeed());
                setVelocityY(-getMovementSpeed());
                break;
            default:
                setVelocityX(0);
                setVelocityY(0);
        }
    }

    public void bulletCollision(List<Zombie> zombieList) {
        for(Zombie zombie : zombieList)
        if (isColliding(zombie)) {
            this.setAlive(false);
            zombie.setHealthPoints(zombie.getHealthPoints() - this.getDamage());
            if (!zombie.stillAlive()) {
                zombie.setAlive(false);
            }
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
