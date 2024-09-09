package UfoDash;

import java.util.ArrayList;
import java.util.List;

/**
 * The 'GameComponents' class manages various components of the game.
 * It provides access to these components for other parts of the game.
 */
public class GameComponents {
    private List<Ground> grounds = new ArrayList<>();
    private Player player;
    private HeadsUpDisplay headsUpDisplay;
    private EnemyManager enemyManager;
    private AsteroidManager asteroidManager;
    private CollisionHandler collisionHandler;
    private PowerUpManager powerUpManager;
    private ProjectileManager projectileManager;

    /**
     * Constructs a new instance of the 'GameComponents' with the specified parameters.
     *
     * @param panelHeight The height of the game panel
     * @param panelWidth The width of the game panel
     * @param groundHeight The height of the ground
     * @param playerStartY The starting vertical position of the player
     */
    public GameComponents(int panelHeight, int panelWidth, int groundHeight, int playerStartY) {

        grounds.add(new Ground(0, panelHeight - groundHeight, panelWidth, groundHeight));
        grounds.add(new Ground(panelWidth, panelHeight - groundHeight, panelWidth, groundHeight));
        player = new Player(panelWidth / 8, playerStartY);
        headsUpDisplay = new HeadsUpDisplay(player, panelWidth);
        asteroidManager = new AsteroidManager(panelWidth, panelHeight, groundHeight, player);
        collisionHandler = new CollisionHandler();
        powerUpManager = new PowerUpManager(panelWidth, panelHeight, groundHeight);
        enemyManager = new EnemyManager(panelWidth, panelHeight, groundHeight);
        projectileManager = new ProjectileManager();
    }

    /**
     * Returns the list of ground objects in the game.
     *
     * @return a list of 'Ground' objects
     */
    public List<Ground> getGrounds() {
        return grounds;
    }

    /**
     * Returns a reference to the player.
     *
     * @return the player object
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the heads-up display (HUD) for the game.
     *
     * @return the 'HeadsUpDisplay' object
     */
    public HeadsUpDisplay getHeadsUpDisplay() {
        return headsUpDisplay;
    }

    /**
     * Returns the enemy manager, responsible for managing enemies.
     *
     * @return The 'EnemyManager' object
     */
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    /**
     * Returns the asteroid manager, responsible for managing asteroids.
     *
     * @return The 'AsteroidManager' object
     */
    public AsteroidManager getAsteroidManager() {
        return asteroidManager;
    }

    /**
     * Returns the collision handler, responsible for detecting and handling collisions.
     *
     * @return The 'CollisionHandler' object
     */
    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    /**
     * Returns the power-up manager, responsible for managing power-ups.
     *
     * @return The 'PowerUpManager' object
     */
    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }

    /**
     * Returns the projectile manager, responsible for managing projectiles.
     *
     * @return The 'ProjectileManager' object
     */
    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }
}
