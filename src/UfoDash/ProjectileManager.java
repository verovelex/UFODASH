package UfoDash;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The 'ProjectileManager' class is responsible for managing all the
 * projectiles in the game. It handles adding, updating, drawing and
 * removing projectiles.
 */
public class ProjectileManager implements Drawable{
    private List<Projectile> projectiles;

    /**
     * Initializes the 'ProjectileManager' by creating an empty
     * list to store projectiles.
     */
    public ProjectileManager() {
        projectiles = new ArrayList<>();
    }

    /**
     * Adds a new projectile to the list of active projectiles.
     *
     * @param projectile The projectile to be added
     */
    public void addProjectile(Projectile projectile){
        projectiles.add(projectile);
    }

    /**
     * Draws all active projectiles on the screen.
     *
     * @param g the Graphics object used to draw the object
     */
    @Override public void draw(final Graphics g) {
        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }
    }

    /**
     * Updates the position of all projectiles and removes any that
     * have moved off-screen.
     */
    @Override public void update() {
        for (Projectile projectile : projectiles) {
            projectile.update();
        }

        projectiles.removeIf(projectile -> projectile.getX() + projectile.getWidth() < 0);
    }

    /**
     * Returns the list of active projectiles.
     *
     * @return a list of projectiles
     */
    public List<Projectile> getProjectiles() {
        return projectiles;
    }
}
