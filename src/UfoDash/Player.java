package UfoDash;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * The 'Player' class represents the main character controlled by the player.
 * It extends the 'GameObjects' class and provides various functionalities,
 * such as movement, jumping, activating shields and handling collisions.
 */
public class Player extends GameObjects{
    private static final int SHIELD_GAP = 10;
    private static final int SHIELD_ARC_ANGLE = 360;
    private static final int DEFAULT_HEIGHT = 70;
    private static final int DEFAULT_WIDTH = 90;
    private static final int MAX_LIVES = 3;
    private static final int COLLISION_DURATION = 200;
    private static final int JUMP_STRENGTH = -15;
    private static final int MAX_VELOCITY_Y = 15;

    private int velocityY;
    private Image playerImg, playerCollidedImg;
    private int lives = MAX_LIVES;
    private boolean isShieldActive = false;
    private boolean isCollided = false;
    private Timer shieldTimer = null, collisionTimer = null;
    private ScoreHandler scoreHandler;

    /**
     * Constructs a 'Player' object with the specified starting position.
     * It also loads the player's images.
     *
     * @param startX The initial x-coordinate of the player
     * @param startY The initial y-coordinate of the player
     */
    public Player(int startX, int startY) {
        super(startX, startY, DEFAULT_WIDTH, DEFAULT_HEIGHT, "ufo.png");
        this.playerImg = getImage();
        this.playerCollidedImg = loadCollidedImage("ufoCollision.png");
        this.scoreHandler = new ScoreHandler();
    }

    /**
     * Loads the image to be used when the player is in a collision state.
     *
     * @param imagePath The path to the collision image
     * @return the loaded image, or null if the image can't be found or loaded
     */
    private Image loadCollidedImage(String imagePath){
        Image img = null;
        try {
            URL imageURL = ClassLoader.getSystemResource("images/" + imagePath);
            if(imageURL != null){
                img = ImageIO.read(imageURL);
                if(img == null){
                    System.err.println("Failed to load image: " + imagePath);
                }
            }else {
                System.err.println("Image not found: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return img;
        }
        return img;
    }

    /**
     * Draws the player on the screen.
     * If the player has collided and the shield is not active, it draws the collided image.
     * If the shield is active, it draws a shield around the player.
     * If the image is not loaded, a rectangle is drawn instead.
     *
     * @param g the Graphics object used to draw the object
     */
    @Override public void draw(final Graphics g) {
        // Choose the correct image based on the collision state
        if (playerImg != null) {
            if(isCollided && !isShieldActive){
                g.drawImage(playerCollidedImg, x, y, null);
            }else{
                g.drawImage(playerImg, x, y, null);
            }
        }else{
            g.setColor(Color.GREEN);
            g.fillRect(x, y, getWidth(), getHeight());
        }

        // Draw shield if it's active
        if(isShieldActive){
            g.setColor(Color.CYAN);
            g.drawArc(x - SHIELD_GAP, y - SHIELD_GAP, getWidth() + SHIELD_GAP * 2, getHeight() + SHIELD_GAP * 2, 0, SHIELD_ARC_ANGLE);
        }
    }

    /**
     * Updates the player's x-position by applying gravity.
     */
    @Override public void update(){
        // Checks so the player doesn't fall to fast
        if(velocityY < MAX_VELOCITY_Y){
            int gravity = 1;
            velocityY += gravity;
        }
        y += velocityY;
    }

    /**
     * The player shoots a projectile by creating a new 'Projectile' object
     * and adding it to the 'ProjectManager'.
     *
     * @param projectileManager The manager that handles all projectiles
     */
    public void shoot(ProjectileManager projectileManager){
        projectileManager.addProjectile(new Projectile(x + DEFAULT_WIDTH, y + DEFAULT_HEIGHT / 2));
    }

    /**
     * Starts a timer for a specific duration and executes the provided callback
     * when the timer finishes. If an existing timer is running, it's stopped.
     *
     * @param duration The duration of the timer in milliseconds
     * @param existingTimer The timer to check and stop if running
     * @param onFinish The action to perform when the timer finishes
     */
    private void startTimer(int duration, Timer existingTimer, Runnable onFinish) {
        if (existingTimer != null && existingTimer.isRunning()) {
            existingTimer.stop();
        }
        Timer newTimer = new Timer(duration, e -> {
            onFinish.run();
            ((Timer)e.getSource()).stop();
        });
        newTimer.setRepeats(false);
        newTimer.setCoalesce(true);
        newTimer.start();
    }

    /**
     * Activates a protective shield around the player for a specific duration.
     *
     * @param duration The duration of the shield in milliseconds
     */
    public void activateShield(int duration){
        isShieldActive = true;
        startTimer(duration, shieldTimer, () -> isShieldActive = false);
    }

    /**
     * Sets the collision state of the player and starts a timer to reset
     * the state after a specified duration.
     *
     * @param collided True if the player has collided, false otherwise
     */
    public void setCollided(final boolean collided) {
        isCollided = collided;
        if (collided) {
            startTimer(COLLISION_DURATION, collisionTimer, () -> isCollided = false);
        }
    }

    /**
     * Makes the player jump by setting a negative velocity (upward movement).
     * Ensures the player doesn't move above the screen.
     */
    public void jump() {
        velocityY = JUMP_STRENGTH;
        y = Math.max(y, 0); //Prevents the player from moving off-screen above
    }

    /**
     * Increases the player's score by one point.
     */
    public void increaseScore(){
        scoreHandler.increaseScore();
    }

    /**
     * Decreases the player's lives by one if the shield is not active.
     */
    public void decreaseLives(){
        if(!isShieldActive){
            lives--;
        }
    }

    /**
     * Increases the player's lives by one, up to the maximum number of lives.
     */
    public void addLives(){
        if(lives < MAX_LIVES){
            lives++;
        }
    }

    /**
     * Returns the player's current score.
     *
     * @return the current score
     */
    public int getCurrentScore(){
        return scoreHandler.getCurrentScore();
    }

    /**
     * Returns the player's best score.
     *
     * @return the best score
     */
    public int getBestScore(){
        return scoreHandler.getBestScore();
    }

    /**
     * Returns the maximum number of lives the player can have.
     *
     * @return the maximum number of lives
     */
    public int getMaxLives(){
        return MAX_LIVES;
    }

    /**
     * Returns the current number of lives the player has.
     *
     * @return the current number of lives
     */
    public int getLives(){
        return lives;
    }

    /**
     * Gets the width of the player.
     * If the image is loaded, its width is returned; otherwise, a default width is used.
     *
     * @return the width
     */
    @Override public int getWidth() {
        if (playerImg != null) {
            return playerImg.getWidth(null);
        } else {
            return DEFAULT_WIDTH;
        }
    }

    /**
     * Gets the height of the player.
     * If the image is loaded, its height is returned; otherwise, a default height is used.
     *
     * @return the height
     */
    @Override public int getHeight() {
        if (playerImg != null) {
            return playerImg.getHeight(null);
        } else {
            return DEFAULT_HEIGHT;
        }
    }

    /**
     * Sets the y-coordinate of the player.
     *
     * @param y the new y-coordinate
     */
    public void setY(final int y) {
        this.y = y;
    }
}
