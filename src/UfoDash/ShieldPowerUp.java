package UfoDash;

import java.awt.*;

/**
 * The 'ShieldPowerUp' class represents a power-up that grants a temporary
 * shield to the player. It extends the 'PowerUp' class and defines the specific
 * behaviour of this type of power-up.
 */
public class ShieldPowerUp extends PowerUp{
    private static final int SHIELD_DURATION = 5000;

    /**
     * Constructs a 'ShieldPowerUp' object with its positions, size and speed.
     *
     * @param x The x-coordinate of the power-up
     * @param y The y-coordinate of the power-up
     * @param width The width of the power-up
     * @param height The height of the power-up
     * @param speed The speed at which the power-up moves across the screen
     */
    public ShieldPowerUp(final int x, final int y, final int width, final int height, final int speed) {
        super(x, y, width, height, speed, "shield.png");
    }

    /**
     * Draws the power-up on the screen if its active.
     * If the image is not loaded, a blue circle is drawn instead.
     *
     * @param g the Graphics object used to draw the object.
     */
    @Override public void draw(final Graphics g) {
        if (isActive) {
            if (image != null) {
                g.drawImage(image, x, y, width, height, null);
            }else{
                g.setColor(Color.BLUE);
                g.fillOval(x, y, width, height);
            }
        }
    }

    /**
     * Applies the effect of the power-up to the player by activating a temporary
     * shield, that prevents the player from losing lives upon collision.
     * After applying the effect, the power-up is deactivated.
     *
     * @param player Reference to the player object
     */
    @Override public void applyEffect(final Player player) {
        player.activateShield(SHIELD_DURATION);
        deactivate();
    }
}
