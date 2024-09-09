package UfoDash;

import java.awt.*;

/**
 * The 'Enemy' class represents an enemy object in the game.
 * The enemy moves horizontally and vertically within defined boundaries.
 */
public class Enemy extends GameObjects{
    private int minY, maxY;
    private Image enemyImage;
    private boolean movingDown = true;
    private static final int DEFAULT_HEIGHT = 60;
    private static final int DEFAULT_WIDTH = 60;
    private static final int VERTICAL_SPEED = 8;
    private static final int HORIZONTAL_SPEED = 5;

    /**
     * Constructs an 'Enemy' object with the given starting position and panel boundaries.
     *
     * @param x The initial x-coordinate of the enemy
     * @param y The initial y-coordinate of the enemy
     * @param panelHeight the height of the game panel
     * @param groundHeight the height of the ground
     */
    public Enemy(final int x, final int y, final int panelHeight, final int groundHeight) {
        super(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT, "ship.png");
        this.minY = 0;
        this.maxY = panelHeight - groundHeight - getWidth();
        this.enemyImage = getImage();
    }

    /**
     * Draws the enemy on the screen.
     * If the image is not loaded, a rectangle is drawn instead.
     *
     * @param g the Graphics object used to draw the object.
     */
    @Override public void draw(final Graphics g) {
        if(enemyImage != null){
            g.drawImage(enemyImage, x, y, null);
        }else{
            g.setColor(Color.ORANGE);
            g.fillRect(x, y, getWidth(), getHeight());
        }
    }

    /**
     * Updates the enemy's x-position based on its movement speed and direction.
     */
    @Override public void update() {
        x -= HORIZONTAL_SPEED;
        if(movingDown){
            y += VERTICAL_SPEED;
            if(y >= maxY){
                movingDown = false;
            }
        }else{
            y -= VERTICAL_SPEED;
            if (y <= minY){
                movingDown = true;
            }
        }
    }

    /**
     * Gets the width of the enemy.
     * If the image is loaded, its width is returned; otherwise, a default width is used.
     *
     * @return the width
     */
    @Override public int getWidth() {
        if (enemyImage != null) {
            return enemyImage.getHeight(null);
        } else {
            return DEFAULT_WIDTH;
        }
    }

    /**
     * Gets the height of the enemy.
     * If the image is loaded, its height is returned; otherwise, a default height is used.
     *
     * @return the height
     */
    @Override public int getHeight() {
        if (enemyImage != null) {
            return enemyImage.getWidth(null);
        } else {
            return DEFAULT_HEIGHT;
        }
    }
}
