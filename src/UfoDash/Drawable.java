package UfoDash;

import java.awt.*;

/**
 * The 'Drawable' interface defines methods for objects that can be drawn on the screen
 * and update in a game loop. Any class implementing this interface should provide
 * concrete implementations of the draw and update methods.
 */
public interface Drawable {
    /**
     * Draws the object on the screen using the provided Graphics object.
     *
     * @param g the Graphics object used to draw the object
     */
    void draw(Graphics g);

    /**
     * Updates the state of the object, typically called once per frame in a game loop.
     */
    void update();
}
