package UfoDash;

import java.awt.*;

/**
 * The abstract class 'GameObjects' represents a game object in the game.
 * It defines common properties like position, size and image for all game objects.
 */
public abstract class GameObjects extends AbstractDrawable{
    protected int x, y, width, height;
    protected Image image;

    /**
     * Constructs a new game object with specified position, size and image.
     *
     * @param x The x-coordinate of the object
     * @param y The y-coordinate of the object
     * @param width The width of the object
     * @param height The height of the object
     * @param imagePath The path to the image file used to represent the object
     */
    protected GameObjects(final int x, final int y, final int width, final int height, final String imagePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = loadImage(imagePath);
    }

    /**
     * Gets the x-coordinate of the game object.
     *
     * @return the x-coordinate
     */
    public int getX(){
        return x;
    }

    /**
     * Gets the y-coordinate of the game object.
     *
     * @return the y-coordinate
     */
    public int getY(){
        return y;
    }

    /**
     * Gets the width of the game object.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the game object.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the image representing the game object.
     *
     * @return the image
     */
    public Image getImage(){
        return image;
    }
}
