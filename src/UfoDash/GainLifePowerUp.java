package UfoDash;

import java.awt.*;

/**
 * The 'GainLifePowerUp' class represents a power-up that grants an extra
 * life to the player. It extends the 'PowerUp' class and defines the specific
 * behaviour of this type of power-up.
 */
public class GainLifePowerUp extends PowerUp{
    /**
     * Constructs a 'GainLifePowerUp' object with its positions, size and speed.
     *
     * @param x The x-coordinate of the power-up
     * @param y The y-coordinate of the power-up
     * @param width The width of the power-up
     * @param height The height of the power-up
     * @param speed The speed at which the power-up moves across the screen
     */
    public GainLifePowerUp(final int x, final int y, final int width, final int height, final int speed) {
        super(x, y, width, height, speed, "heart.png");
    }

    /**
     * Draws the power-up on the screen if its active.
     * If the image is not loaded, a red circle is drawn instead.
     *
     * @param g the Graphics object used to draw the object.
     */
    @Override public void draw(final Graphics g) {
        if (isActive) {
            if (getImage() != null) {
                g.drawImage(image, x, y, width, height, null);
            }else{
                g.setColor(Color.RED);
                g.fillOval(x, y, width, height);
            }
        }
    }

    /**
     * Applies the effect of the power-up to the player by increasing the player's
     * life count. After applying the effect, the power-up is deactivated.
     *
     * @param player Reference to the player object
     */
    @Override public void applyEffect(final Player player) {
        player.addLives();
        deactivate();
    }
}
