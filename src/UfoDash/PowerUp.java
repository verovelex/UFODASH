package UfoDash;

import java.awt.*;

/**
 * The 'PowerUp' abstract class represents a power-up item in the game that
 * provides benefits to the player. This class defines common properties and
 * behaviours for specific types of power-ups.
 */
public abstract class PowerUp extends AbstractDrawable{
    protected int x, y, width, height, speed;
    protected boolean isActive;
    protected Image image;

    /**
     * Constructs a new power-up with specified position, size and image.
     *
     * @param x The x-coordinate of the power-up
     * @param y The y-coordinate of the power-up
     * @param width The width of the power-up
     * @param height The height of the power-up
     * @param speed The speed of the power-up
     * @param imagePath The path to the image file used to represent the power-up
     */
    protected PowerUp(final int x, final int y, final int width, final int height, final int speed, final String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.isActive = true; // The power-up is active from the start
        this.image = loadImage(imagePath);
    }

    /**
     * Returns whether the power-up is active or not.
     *
     * @return true if power-up is active, false otherwise
     */
    public boolean isActive(){
        return isActive;
    }

    /**
     * Deactivates the power-up, removing it from the game.
     */
    public void deactivate(){
        isActive = false;
    }

    /**
     * Gets the x-coordinate of the power-up.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the power-up.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the width of the power-up.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the power-up.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the image representing the power-up.
     *
     * @return the image
     */
    public Image getImage(){
        return image;
    }

    /**
     * Updates the position of the power-up based on its speed.
     * If the power-up moves off the screen, it is automatically deactivated.
     */
    @Override public void update() {
        x-=speed;
        if(x + width < 0){
            deactivate();
        }
    }

    /**
     * Abstract method that applies the effect of the power-up to the player.
     * This method must be implemented by power-up subclasses to define specific effects.
     *
     * @param player Reference to the player object
     */
    public abstract void applyEffect(Player player);
}
