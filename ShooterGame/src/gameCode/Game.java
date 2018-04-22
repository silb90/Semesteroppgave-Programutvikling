package gameCode;

import entities.*;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Pane gameWindow;
    private Player player;
    private List<Zombie> zombies;
    private Text playerHP, playerArmor, magazineSize, poolSize, score, equippedWeapon;

    private boolean isRunning = true;
    private boolean createDrops = true;
    private boolean holdingButtonR;

    private InitializeGame controller;

    private List<Bullet> bullets;
    private List<Drop> drops = new ArrayList<>();
    private List<Drop> dropsExtra = new ArrayList<>();

    private int scoreNumber = 0;

    public Game(Player player, List <Zombie> zombies, Pane gameWindow, Text playerHP, Text playerArmor, Text magazineSize, Text poolSize, Text score, Text equippedWeapon){

        this.gameWindow = gameWindow;
        this.player = player;
        this.zombies = zombies;
        this.playerHP = playerHP;
        this.playerArmor = playerArmor;
        this.magazineSize = magazineSize;
        this.poolSize = poolSize;
        this.score = score;
        this.equippedWeapon = equippedWeapon;

        final long startNanoTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double time = (now - startNanoTime) / 1000000000.0;
                onUpdate(time);
                updateHUD();
            }
        };
        timer.start();
    }

    /***
     * Method which continuously run as long as isRunning is set to true.
     * Method will keep updating all Entities' positions and check for collision.
     * Method is affected by pauseGame() method.
     * @param time Requires a double value which here continuously gets updated via the
     *             the AnimationTimer
     */
    private void onUpdate(double time) {
        if (!isRunning && holdingButtonR){
            restartGame();
            controller.setGameLabel(false, false);
        }
        if (isRunning) {

            bullets = player.getBulletList();

            // Create Drop entities with random position
            if (dropsExtra.size() < 10 && createDrops) {
                int random = (int) Math.floor(Math.random() * 100);
                if (random < 4) {
                    int x = (int) Math.floor(Math.random() * gameWindow.getWidth());
                    int y = (int) Math.floor(Math.random() * gameWindow.getHeight());

                    dropsExtra.add(new Drop("/resources/Art/Icon/hp_icon.png", x, y));
                }
            }

            // Check collision between zombies and player
            for (Zombie zombie : zombies) {
                zombie.movement(player);
                if (player.isColliding(zombie)) {
                    player.receivedDamage(10);
                    if (!player.stillAlive()) {
                        System.out.println("Player is dead");
                        gameOver();
                    }
                }
            }

            // Draw bullets to the pane, adjust direction, and check collision with zombies
            for (Bullet bullet : bullets) {
                if (!bullet.isDrawn()) {
                    gameWindow.getChildren().addAll(bullet.getIv(), bullet.getNode());
                    bullet.setDrawn();
                }
                bullet.bulletDirection();
                bullet.bulletCollision(zombies);
            }

            // Check collision between drops and player
            for (Drop drop : drops) {
                if(drop.isColliding(player)) {
                    drop.randomPickup(player);
                    drop.setAlive(false);
                }
            }

            // Draw dropsExtra to the pane, and check for collision with player
            for (Drop drop : dropsExtra) {
                if (!drop.isDrawn()) {
                    gameWindow.getChildren().addAll(drop.getNode(), drop.getIv());
                    drop.setDrawn();
                }
                if (drop.isColliding(player)) {
                    drop.setAlive(false);
                }
            }

            // Update animation, position and velocity of player
            player.updateAnimation();
            player.update(time);

            // Update zombie position and velocity.
            // Check if zombie is alive. If dead, create and draw a Drop entity
            for(Zombie zombie : zombies) {
                if(!zombie.isAlive()) {
                    this.scoreNumber += 100;
                    gameWindow.getChildren().removeAll(zombie.getNode(), zombie.getIv());
                    Drop drop = new Drop("/resources/Art/Icon/circle_icon.png", zombie.getPositionX(), zombie.getPositionY());
                    drops.add(drop);

                    if (!drop.isDrawn()) {
                        gameWindow.getChildren().add(drop.getNode());
                        drop.setDrawn();
                    }
                }
                zombie.update(time);
            }

            // Check if Bullet is dead, and remove if so
            for(Bullet bullet : bullets) {
                if(!bullet.isAlive())
                    gameWindow.getChildren().removeAll(bullet.getIv(), bullet.getNode());
                bullet.update(time);
            }

            // Check if Drop of drops is dead
            for(Drop drop : drops) {
                if(!drop.isAlive())
                  gameWindow.getChildren().removeAll(drop.getNode(), drop.getIv());
            }

            // Check if Drop of dropsExtra is dead, add 25 points to score if so
            for(Drop drop : dropsExtra) {
                if(!drop.isAlive()) {
                    scoreNumber += 25;
                    gameWindow.getChildren().removeAll(drop.getNode(), drop.getIv());
                }
            }

            // Remove every dead Entity object from the ArrayLists
            bullets.removeIf(Bullet::isDead);
            zombies.removeIf(Zombie::isDead);
            drops.removeIf(Drop::isDead);
            dropsExtra.removeIf(Drop::isDead);
        }
    }

    public List<Bullet> getBulletList() {
        return this.bullets;
    }

    public List<Drop> getDrops() {
        return this.drops;
    }

    public List<Drop> getDropsExtra() {
        return this.dropsExtra;
    }

    /**
     * Method for updating the datafields playerHP, magazineSize, poolSize of type Text.
     * These will in turn update the Text values on the HUD.
     */
    public void updateHUD() {
        String hpLevel = String.valueOf(player.getHealthPoints());
        String armorLevel = String.valueOf(player.getArmor());
        String magazineLevel = String.valueOf(player.getMagazineCount());
        String poolLevel = String.format("%02d", player.getAmmoPool());
        String score = String.format("%05d", this.scoreNumber);
        String weapon = player.getWeaponTypeToString().toUpperCase();

        this.playerHP.setText(hpLevel);
        this.playerArmor.setText(armorLevel);
        this.magazineSize.setText(magazineLevel);
        this.poolSize.setText(poolLevel);
        this.score.setText(score);
        this.equippedWeapon.setText(weapon);
    }

    /***
     * Method for changing the boolean isRunning.
     * Method affects the onUdate() function.
     */
    public void pauseGame() {
        if (isRunning) {
            setRunning(false);
            controller.setGameLabel(false, true);
        } else {
            setRunning(true);
            controller.setGameLabel(false, false);
        }
    }

    /***
     * Method for stopping the game and displaying a message at a
     * point where the game is over.
     */
    public void gameOver() {
        setRunning(false);
        controller.setGameLabel(true, true);
    }

    /***
     * Method for restarting the game when GameOver or Paused
     * The method will first try to remove all zombies and bonuses on the stage.
     * Then set the player's position equals to the player's original start position,
     * as well as resetting the player's hp, armor and score to it's original value.
     * The method will then try to respawn all the zombies.
     * Then set both "isRunning" and "createDrops" equals "true"
     * as well as setting both "isGameOver" and "gameIsPaused" equals "false",
     * which allows this method to run again after restarting the game
     */
    private void restartGame() {
        for (Zombie zombie : zombies) {
            gameWindow.getChildren().remove(zombie.getSprite().getImageView());
            gameWindow.getChildren().remove(zombie.getNode());
        }
        zombies.clear();

        player.resetPlayer();
        this.scoreNumber = 0;

        // Må Fikse å hente zombie animasjon her ala som i initializegame
        try {
//            for (int i = 0; i < 10; i++) {
//                zombies.add(new Zombie("/resources/Art/Zombie/skeleton-idle_", ".png", 17, (int) (Math.random() * 1280), (int) (Math.random() * 720), 100));
//            }
        } catch (Exception e) {
            System.out.println("Error: Enemies did not load correctly");
        }

        for (Zombie zombie : zombies) {
            gameWindow.getChildren().addAll(zombie.getNode());
            gameWindow.getChildren().addAll(zombie.getSprite().getImageView());
        }

        setRunning(true);
    }

    public int getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(int scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void setHoldingButtonR(boolean holdingButtonR) {
        this.holdingButtonR = holdingButtonR;
    }

    public void setController(InitializeGame controller) {
        this.controller = controller;
    }
}