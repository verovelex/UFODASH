package UfoDash;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles collisions between various game objects such as the player,
 * enemies, projectiles, ground, asteroids and power-ups.
 * It also manages the cool-down period between collision to prevent multiple
 * collisions from being registered too quickly.
 */
public class CollisionHandler {
    /** Manages cool-down period for collisions. */
    private long lastCollisionTime;
    private static final long COLLISION_COOL_DOWN = 1000;

    public CollisionHandler() {
        lastCollisionTime = 0;
    }

    /**
     * Checks if cool-down period for collisions is over.
     *
     * @return true if the cool-down period is over, otherwise false.
     */
    private boolean isCollisionCoolDownOver(){
        long currentTime = System.currentTimeMillis();
        return currentTime - lastCollisionTime > COLLISION_COOL_DOWN;
    }

    /**
     * Handles all possible collisions between the player and asteroids, ground, enemies and power-ups.
     * Additionally, handles collisions between projectiles and enemies.
     *
     * @param player The player being checked for collisions.
     * @param asteroids The list of asteroids that the player can collide with.
     * @param powerUps The list of power-ups that the player can collect.
     * @param enemies The list of enemies that the player can collide with.
     * @param projectiles The list of projectiles shot by the player.
     * @param groundHeight The height of the ground.
     * @param panelHeight The height of the panel where the game is displayed.
     * @param playerY The player's Y position for resetting when colliding with the ground.
     */
    public void processCollisions(Player player, List<Asteroid> asteroids, List<PowerUp> powerUps, List<Enemy> enemies,
                                  List<Projectile> projectiles, int groundHeight, int panelHeight, int playerY){
        if(isCollisionCoolDownOver()){
            boolean playerCollidingWithAsteroid = isPlayerCollidingWithAsteroids(player, asteroids);
            boolean playerCollidingWithGround = isPlayerCollidingWithGround(player, groundHeight, panelHeight);
            boolean playerCollidingWithEnemies = isPlayerCollidingWithEnemies(player, enemies);

            // Checks if player is colliding with any of the elements
            if(playerCollidingWithAsteroid || playerCollidingWithGround || playerCollidingWithEnemies){
                lastCollisionTime = System.currentTimeMillis();
                player.decreaseLives();
                player.setCollided(true);

                // If player is colliding with the ground, reset their Y position
                if (playerCollidingWithGround) {
                    player.setY(playerY);
                }
            }
        }
        handlePlayerPowerUpCollision(player, powerUps);
        handleProjectileEnemyCollisions(projectiles, enemies);
    }

    /**
     * Checks if the player is colliding with any asteroid.
     *
     * @param player The player being checked for collisions.
     * @param asteroids The list of asteroids that the player can collide with.
     * @return true if the player is colliding with an asteroid, otherwise false.
     */
    private boolean isPlayerCollidingWithAsteroids(Player player, List<Asteroid> asteroids){
        Rectangle2D playerRect = new Rectangle2D.Double(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for(Asteroid asteroid : asteroids){
            Ellipse2D asteroidEllipse = new Ellipse2D.Double(asteroid.getX(), asteroid.getY(), asteroid.getWidth(), asteroid.getHeight());
            if (asteroidEllipse.intersects(playerRect)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the player is colliding with the ground.
     *
     * @param player The player being checked for collisions.
     * @param groundHeight The height of the ground.
     * @param panelHeight The height of the panel where the game is displayed.
     * @return true if the player is colliding with the ground, otherwise false.
     */
    private boolean isPlayerCollidingWithGround(Player player, int groundHeight, int panelHeight){
        return player.getY() + player.getHeight() >= panelHeight - groundHeight;
    }

    /**
     * Checks if the player is colliding with any enemies.
     *
     * @param player The player being checked for collisions.
     * @param enemies The list of enemies that the player can collide with.
     * @return true if the player is colliding with an enemy, otherwise false.
     */
    private boolean isPlayerCollidingWithEnemies(Player player, List<Enemy> enemies){
        Rectangle2D playerRect = new Rectangle2D.Double(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for(Enemy enemy : enemies){
            Rectangle2D enemyRect = new Rectangle2D.Double(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
            if (enemyRect.intersects(playerRect)){
                return true;
            }
        }
        return false;
    }

    /**
     * Handles collisions between the player and power-ups.
     *
     * @param player The player being checked for collisions.
     * @param powerUps The list of power-ups that the player can collect.
     */
    private void handlePlayerPowerUpCollision(Player player, List<PowerUp> powerUps) {
        Rectangle2D playerRect = new Rectangle2D.Double(player.getX(), player.getY(), player.getWidth(),
                player.getHeight());
        for (PowerUp powerUp : powerUps) {
            Ellipse2D powerUpEllipse = new Ellipse2D.Double(powerUp.getX(), powerUp.getY(), powerUp.getWidth(), powerUp.getHeight());
            if (powerUp.isActive() && powerUpEllipse.intersects(playerRect)){
                powerUp.applyEffect(player);
                powerUp.deactivate();
            }
        }
    }

    /**
     * Handles collisions between projectiles and enemies.
     *
     * @param projectiles The list of projectiles shot by the player.
     * @param enemies The list of enemies that the player can collide with.
     */
    private void handleProjectileEnemyCollisions(List<Projectile> projectiles, List<Enemy> enemies){
        List<Projectile> projectilesToRemove = new ArrayList<>();
        List<Enemy> enemiesToRemove = new ArrayList<>();

        for(Projectile projectile : projectiles){
            Rectangle2D projectileRect = new Rectangle2D.Double(projectile.getX(), projectile.getY(), projectile.getWidth(), projectile.getHeight());

            for(Enemy enemy : enemies) {
                Rectangle2D enemyRect = new Rectangle2D.Double(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());

                if (enemyRect.intersects(projectileRect)) {
                    projectilesToRemove.add(projectile);
                    enemiesToRemove.add(enemy);
                    break; // A projectile can only hit one enemy at a time
                }
            }
        }
        projectiles.removeAll(projectilesToRemove);
        enemies.removeAll(enemiesToRemove);
    }
}
