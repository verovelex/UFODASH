package UfoDash;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The 'PowerUpManager' class is responsible for managing the creation,
 * movement and removal of power-ups in the game.
 */
public class PowerUpManager extends Manager{
    private static final int SIZE = 60;
    private static final int SPEED = 5;
    private List<PowerUp> powerUps;

    /**
     * Constructs an 'PowerUpManager' with the specified panel dimensions and
     * the ground height.
     *
     * @param panelWidth The width of the game panel
     * @param panelHeight The height of the game panel
     * @param groundHeight The height of the ground
     */
    protected PowerUpManager(final int panelWidth, final int panelHeight, final int groundHeight) {
        super(panelWidth, panelHeight, groundHeight);
        this.powerUps = new ArrayList<>();
    }

    /**
     * Creates and add a new power-up at a random y-position within the visible area of the game panel.
     * A random power-up type is selected either 'GainLifePowerUp' or 'ShieldPowerUp'.
     */
    @Override public void spawn() {
        int y = RANDOM.nextInt(panelHeight - groundHeight - SIZE);
        PowerUp newPowerUp = (RANDOM.nextBoolean()) ? new GainLifePowerUp(panelWidth, y, SIZE, SIZE, SPEED)
                : new ShieldPowerUp(panelWidth, y, SIZE, SIZE, SPEED);
        powerUps.add(newPowerUp);
    }

    /**
     * Removes the power-ups that have moved off the screen to the left.
     */
    @Override public void removeOffScreenObjects() {
        powerUps.removeIf(powerUp -> powerUp.getX() + powerUp.getWidth() < 0);
    }

    /**
     * Draws all power-ups on the screen.
     *
     * @param g the Graphics object used to draw the object
     */
    @Override public void draw(final Graphics g) {
        for(PowerUp powerUp : powerUps){
            if(powerUp.isActive()){
                powerUp.draw(g);
            }
        }
    }

    /**
     * Updates the position of each power-up and removes those that have left the screen.
     */
    @Override public void update() {
        for(PowerUp powerUp : powerUps){
            powerUp.update();
        }
        removeOffScreenObjects();
    }

    /**
     * Returns the interval in milliseconds at which the new power-ups are spawned.
     *
     * @return the spawn interval for power-ups
     */
    @Override public int getSpawnerInterval() {
        return 2000;
    }

    /**
     * Returns the list of power-ups.
     *
     * @return a list of power-ups currently managed by the 'PowerUpManager'
     */
    public List<PowerUp> getPowerUps() {
        return powerUps;
    }
}
