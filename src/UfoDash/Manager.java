package UfoDash;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Random;

/**
 * Abstract base class for managing spawning of game objects.
 * Provides methods to start and stop spawning, and handle common spawning logic.
 */
public abstract class Manager implements Drawable{
    protected int panelWidth, panelHeight, groundHeight;
    protected boolean spawnerStarted = false;
    protected Timer timer = null;
    protected static final Random RANDOM = new Random();

    /**
     * Constructs the 'Manager' with specified dimensions.
     *
     * @param panelWidth The width of the game panel
     * @param panelHeight The height of the game panel
     * @param groundHeight The height of the ground
     */
    protected Manager(final int panelWidth, final int panelHeight, final int groundHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.groundHeight = groundHeight;
    }

    /**
     * Starts the spawning process if it's not already running.
     * Uses timer to periodically create new objects based on the spawn interval.
     */
    public void startSpawner(){
        if(!spawnerStarted){
            final Action spawner = new AbstractAction(){
                @Override public void actionPerformed(final ActionEvent e) {
                    spawn();
                }
            };
            timer = new Timer(getSpawnerInterval(), spawner);
            timer.setCoalesce(true);
            timer.start();
            spawnerStarted = true;
        }
    }

    /**
     * Stop spawning process if it's running.
     */
    public void stopSpawner(){
        if(spawnerStarted && timer != null){
            timer.stop();
            spawnerStarted = false;
        }
    }

    /**
     * Returns if the spawning process has started.
     *
     * @return true if spawner is started, false otherwise
     */
    public boolean isSpawnerStarted() {
        return spawnerStarted;
    }

    /**
     * Abstract method to define how often new objects should be spawned.
     *
     * @return the interval between spawns in milliseconds
     */
    public abstract int getSpawnerInterval();

    /**
     * Abstract method to spawn new objects. This method should be implemented by subclasses
     * to specify what type of objects to spawn.
     */
    public abstract void spawn();

    /**
     * Abstract method should be implemented by subclasses to remove objects that have gone off-screen.
     */
    public abstract void removeOffScreenObjects();
}
