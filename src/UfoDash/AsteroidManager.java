package UfoDash;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The 'AsteroidManager' class is responsible for managing the creation,
 * movement and removal of asteroids in the game.
 */
public class AsteroidManager extends Manager{
    private List<Asteroid> asteroids;
    private Player player;
    private int asteroidHeight, lastScoreCheck;
    private static final int ASTEROID_WIDTH = 110;
    private static final int GAP_HEIGHT = 250;
    private static final int INITIAL_ASTEROID_SPEED = 5;
    private static final int SCORE_THRESHOLD_SPEED_INCREASE = 5;
    private static final double ASTEROID_HEIGHT_RATIO = 2.0/3.0;
    private int currentAsteroidSpeed = INITIAL_ASTEROID_SPEED;

    /**
     * Constructs an 'AsteroidManager' with the size of the game panel,
     * the ground height and a reference to the player.
     *
     * @param panelWidth The width of the game panel
     * @param panelHeight The height of the game panel
     * @param groundHeight The height of the ground
     * @param player Reference to the player object
     */
    protected AsteroidManager(final int panelWidth, final int panelHeight, final int groundHeight, Player player) {
        super(panelWidth, panelHeight, groundHeight);
        this.player = player;
        asteroidHeight =(int) (panelHeight*ASTEROID_HEIGHT_RATIO);
        asteroids = new ArrayList<>();
    }

    /**
     * Creates a new pair of asteroids (top and bottom) with a gap between them.
     * They are spawned at a random vertical positions (within bounds).
     */
    @Override public void spawn() {
        // Randomly determines the y position of the top asteroid based on its height and the ground height
        int topAsteroidY = RANDOM.nextInt(-asteroidHeight, -groundHeight);

        // Calculates the bottom asteroid's y position based on the top asteroid's position and the gap
        int bottomAsteroidY = topAsteroidY + asteroidHeight + GAP_HEIGHT;

        asteroids.add(new Asteroid(panelWidth, topAsteroidY, ASTEROID_WIDTH, asteroidHeight, currentAsteroidSpeed));
        asteroids.add(new Asteroid(panelWidth, bottomAsteroidY, ASTEROID_WIDTH, asteroidHeight, currentAsteroidSpeed));
    }

    /**
     * Removes the asteroids that have moved off the screen.
     */
    @Override public void removeOffScreenObjects() {
        // Checks if asteroid is off-screen to the left
        asteroids.removeIf(asteroid -> asteroid.getX() + asteroid.getWidth() < 0);
    }

    /**
     * Updates the position of each asteroid, checks for score increment and
     * increases asteroid speed if necessary.
     */
    @Override public void update() {
        for (Asteroid asteroid : asteroids) {
            asteroid.update();

            // Checks if asteroid has passed the player, increases score and speed
            if(asteroid.getX() + asteroid.getWidth()/2 <= player.getX() && !asteroid.isHasPassed()){
                player.increaseScore();
                asteroid.setHasPassed(true);
                increaseAsteroidSpeed();
            }
        }
        removeOffScreenObjects();
    }

    /**
     * Draws all asteroids on the screen.
     *
     * @param g the Graphics object used to draw the object
     */
    @Override public void draw(final Graphics g) {
        for ( Asteroid asteroid: asteroids){
            asteroid.draw(g);
        }
    }

    /**
     * Returns the interval in milliseconds at which the new asteroids are spawned.
     *
     * @return the spawn interval for asteroids
     */
    @Override public int getSpawnerInterval() {
        return 2000; //Spawns asteroids every other second
    }

    /**
     * Increases the speed of the asteroids every time the player scores 5 points.
     */
    private void increaseAsteroidSpeed(){
        int score = player.getCurrentScore();
        if((score % SCORE_THRESHOLD_SPEED_INCREASE == 0) && score != lastScoreCheck){
            currentAsteroidSpeed++;
            for(Asteroid asteroid : asteroids ){
                asteroid.setSpeed(currentAsteroidSpeed);
            }
            lastScoreCheck = score;
        }
    }

    /**
     * Returns the list of asteroids.
     *
     * @return a list of asteroids currently managed by the 'AsteroidManager'
     */
    public List<Asteroid> getAsteroids() {
        return asteroids;
    }
}
