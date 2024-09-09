package UfoDash;

import java.awt.*;

/**
 * The 'Asteroid' class represents an asteroid in the game.
 * It extends the 'GameObjects' class and provides functionality specific to asteroid,
 * such as updating its position and drawing it on the screen.
 */
public class Asteroid extends GameObjects{
    /**
     * Flag to check if the asteroid has passed the player's x-position.
     */
    private boolean hasPassed = false;
    private Image asteroidImg;
    private int speed;

    /**
     * Constructs an 'Asteroid' object with a specified position, size and speed.
     * Also, loads the asteroids image from the given path.
     *
     * @param x The x-coordinate of the asteroid
     * @param y The y-coordinate of the asteroid
     * @param width The width of the asteroid
     * @param height The height of the asteroid
     * @param speed The speed at which the asteroid moves across the screen
     */
    public Asteroid(final int x, final int y, final int width, final int height, final int speed) {
        super(x, y, width, height, "asteroid2.png");
        this.speed = speed;
        this.asteroidImg = getImage();
    }

    /**
     * Updates the asteroid's x-position by moving it to the left based on its speed.
     */
    @Override public void update(){
        x -= speed;
    }

    /**
     * Draws the asteroid on the screen.
     * If the image is not loaded, a rectangle is drawn instead.
     *
     * @param g the Graphics object used to draw the object
     */
    @Override public void draw(final Graphics g) {
        if (asteroidImg != null) {
            g.drawImage(asteroidImg, x, y, width, height, null);
        }else{
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y, getWidth(), getHeight());
        }
    }

    /**
     * Returns whether the asteroid has passed the player's x-position
     *
     * @return true if the asteroid has passed, false otherwise
     */
    public boolean isHasPassed(){
        return hasPassed;
    }

    /**
     * Sets the flag indicating whether the asteroid has passed
     * the player's x-position.
     *
     * @param hasPassed The new value for the hasPassed flag
     */
    public void setHasPassed(boolean hasPassed){
        this.hasPassed = hasPassed;
    }

    /**
     * Sets the speed of the asteroid.
     *
     * @param speed The new speed of the asteroid
     */
    public void setSpeed(final int speed) {
        this.speed = speed;
    }
}
