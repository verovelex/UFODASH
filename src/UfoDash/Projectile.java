package UfoDash;

import java.awt.*;

/**
 * The 'Projectiles' class represents a projectile fired by the player.
 * It extends the 'GameObjects' class and provides functionality for
 * updating its position and drawing it on the screen.
 */
public class Projectile  extends GameObjects{
    private Image projectileImage;
    private static final int DEFAULT_WIDTH = 20;
    private static final int DEFAULT_HEIGHT = 10;
    private static final int SPEED = 10;

    /**
     * Construct 'Projectile' object with a given position and loads its image.
     *
     * @param x The initial x-coordinate of the projectile
     * @param y The initial y-coordinate of the projectile
     */
    public Projectile(final int x, final int y) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, "projectile.png");
        this.projectileImage = getImage();
    }

    /**
     * Draws the projectile on the screen.
     * If the image is not loaded, a rectangle is drawn instead.
     *
     * @param g the Graphics object used to draw the object.
     */
    @Override public void draw(final Graphics g) {
        if (projectileImage != null) {
            g.drawImage(projectileImage, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, null);
        }else{
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    }

    /**
     * Updates the projectile's x-position by moving it horizontally at a constant speed.
     */
    @Override public void update() {
        x += SPEED;
    }
}
