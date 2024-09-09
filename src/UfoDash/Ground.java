package UfoDash;

import java.awt.*;

/**
 * The 'Ground' class represents a ground in the game.
 * It extends the 'GameObjects' class and provides functionality specific to ground,
 * such as updating its position to create a scrolling effect and drawing it on the screen.
 */
public class Ground extends GameObjects{
    private Image groundImg;
    private static final int SPEED = 5;

    /**
     * Constructs a 'Ground' object with the specified position and size.
     * The ground image is also loaded during initialization.
     *
     * @param x The initial x-coordinate of the ground
     * @param y The initial y-coordinate of the ground
     * @param width The width of the ground
     * @param height The height of the ground
     */
    public Ground(final int x, final int y, final int width, final int height)
    {
        super(x, y, width, height, "ground.png");
        this.groundImg = getImage();
    }

    /**
     * Updates the position of the ground to create a scrolling effect.
     * When the ground moves off the screen, it resets to the right side.
     */
    @Override public void update(){
        x -= SPEED;
        if(x <= -width){
            // Reset he grounds position to the right side of the screen
            x = width;
        }
    }

    /**
     * Draws the ground on the screen.
     * If the image is not available, a rectangle is drawn instead.
     *
     * @param g the Graphics object used to draw the object.
     */
    @Override public void draw(final Graphics g) {
        if (groundImg != null) {
            g.drawImage(groundImg, x, y, width, height, null);
        }else{
            g.setColor(Color.GRAY);
            g.fillRect(x, y, width, height );
        }
    }
}
