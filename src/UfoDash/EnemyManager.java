package UfoDash;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The 'EnemyManager' class is responsible for managing the creation,
 * movement and removal of enemies in the game.
 */
public class EnemyManager extends Manager{
    private List<Enemy> enemies;

    /**
     * Constructs an 'EnemyManager' with the specified panel dimensions and
     * the ground height.
     *
     * @param panelWidth The width of the game panel
     * @param panelHeight The height of the game panel
     * @param groundHeight The height of the ground
     */
    protected EnemyManager(final int panelWidth, final int panelHeight, final int groundHeight) {
        super(panelWidth, panelHeight, groundHeight);
        enemies = new ArrayList<>();
    }

    /**
     * Creates and add a new enemy at a random y-position within the visible area of the game panel.
     */
    @Override public void spawn() {
        int y = RANDOM.nextInt(panelHeight - groundHeight) + groundHeight;
        enemies.add(new Enemy(panelWidth, y, panelHeight, groundHeight));
    }

    /**
     * Removes the enemies that have moved off the screen.
     */
    @Override public void removeOffScreenObjects() {
        enemies.removeIf(enemy -> enemy.getX() + enemy.getWidth() < 0);
    }

    /**
     * Draws all enemies on the screen.
     *
     * @param g the Graphics object used to draw the object
     */
    @Override public void draw(final Graphics g) {
        for(Enemy enemy : enemies){
            enemy.draw(g);
        }
    }

    /**
     * Updates the position of each enemy and removes those that have left the screen.
     */
    @Override public void update() {
        for(Enemy enemy : enemies){
            enemy.update();
        }
        removeOffScreenObjects();
    }

    /**
     * Returns the interval in milliseconds at which the new enemies are spawned.
     *
     * @return the spawn interval for enemies
     */
    @Override public int getSpawnerInterval() {
        return 4000; //Spawns enemies every 4 seconds
    }

    /**
     * Returns the list of enemies.
     *
     * @return a list of enemies currently managed by the 'EnemyManager'
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
