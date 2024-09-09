package UfoDash;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * The 'HeadsUpDisplay' class manages the display of the player's information,
 * such as lives and score, on the screen during the game.
 */
public class HeadsUpDisplay {
    private Player player;
    private Image fullHeartImg = null;
    private Image emptyHeartImg = null;
    private int panelWidth;
    private static final int HEART_HEIGHT = 60;
    private static final int HEART_WIDTH = 60;
    private static final int PADDING = 10;
    private static final int SCORE_X_POS = 10;
    private static final int SCORE_Y_POS = 50;

    /**
     * Initializes the 'HeadsUpDisplay' with a reference to the player and the panel's width.
     *
     * @param player The player object whose information is displayed
     * @param panelWidth The width of the game panel
     */
    public HeadsUpDisplay(Player player, int panelWidth) {
        this.player = player;
        this.panelWidth = panelWidth;

        try{
            String fullHeartImgPath ="whole_heart.png";
            String emptyHeartImgPath ="no_heart.png";
            URL fullHeartImageURL = ClassLoader.getSystemResource("images/" + fullHeartImgPath);
            URL emptyHeartImageURL = ClassLoader.getSystemResource("images/" + emptyHeartImgPath);

            if(fullHeartImageURL != null){
                fullHeartImg = ImageIO.read(fullHeartImageURL);
                if(fullHeartImg == null){
                    System.err.println("Failed to load image: " + fullHeartImgPath);
                }

            }else {
                System.err.println("Image not found: " + fullHeartImageURL);
            }

            if(emptyHeartImageURL != null){
                emptyHeartImg = ImageIO.read(emptyHeartImageURL);
                if(emptyHeartImg == null){
                    System.err.println("Failed to load image: " + emptyHeartImgPath);
                }
            }else {
                System.err.println("Image not found: " + emptyHeartImageURL);
            }
        }catch (IOException e){
            e.printStackTrace();
            fullHeartImg = null;
            emptyHeartImg = null;
        }
    }

    /**
     * Draws the player's hearts (lives) in the top-right corner of the screen.
     *
     * @param g the Graphics object used to draw the object
     */
    public void drawHeart(Graphics g){
        int lives = player.getLives();
        int maxLives = player.getMaxLives();

        for (int i = 0; i < maxLives; i++) {
            // Calculate the x and y coordinates for positioning the hearts
            int x = panelWidth - (i + 1) * (HEART_WIDTH + PADDING);
            int y = PADDING;

            Image heartImage = (i<lives) ?  fullHeartImg : emptyHeartImg;
            Color fallbackColor = (i<lives) ? Color.RED : Color.GRAY;

            if(heartImage != null){
                g.drawImage(heartImage, x, y, HEART_WIDTH, HEART_HEIGHT, null);
            }else{
                g.setColor(fallbackColor);
                g.fillRect(x, y, HEART_WIDTH, HEART_HEIGHT);
            }
        }
    }

    /**
     * Draws the player's current score in the top-left corner of the screen.
     *
     * @param g the Graphics object used to draw the object
     */
    public void drawScore(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        g.drawString("Score: " + player.getCurrentScore(), SCORE_X_POS, SCORE_Y_POS);
    }
}
