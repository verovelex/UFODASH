package UfoDash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The 'EndState' class represents the end screen of the game.
 * It handles the rendering of the background and the display of
 * final and best scores and the key binding for quitting or re-playing the game.
 */
public class EndState {
    private GameStateHandler gameStateHandler;
    private GamePanel gamePanel;
    private int panelWidth, finalScore, bestScore, boxWidth, boxHeight, boxX, boxY;

    private static final int BOX_WIDTH_RATIO = 4;
    private static final int BOX_HEIGHT_RATIO = 5;
    private static final Color TEXT_COLOR = new Color(255, 185, 0);
    private static final String FONT_NAME = "Comic Sans MS";
    private static final int LARGE_FONT_SIZE = 70;
    private static final int SMALL_FONT_SIZE = 35;
    private static final float TRANSPARENCY_LEVEL = 0.7f;
    private static final float FULL_OPACITY = 1.0f;


    /**
     * Constructs a 'EndState' object with the given game state handler,
     * game panel and panel dimensions.
     *
     * @param gameStateHandler The handler that manages the current game state
     * @param gamePanel The game panel screen where the title screen will be rendered
     * @param panelWidth The width of the game panel
     * @param panelHeight The height of the game panel
     */
    public EndState(final GameStateHandler gameStateHandler, final GamePanel gamePanel, final int panelWidth, final int panelHeight) {
        this.gameStateHandler = gameStateHandler;
        this.gamePanel = gamePanel;
        this.panelWidth = panelWidth;

        // Calculate dimensions and positions for the end screen
        this.boxWidth = panelWidth - panelWidth/BOX_WIDTH_RATIO;
        this.boxHeight = panelHeight - panelHeight/BOX_HEIGHT_RATIO;
        this.boxX = (panelWidth - boxWidth) / 2;
        this.boxY = (panelHeight - boxHeight) / 2;

        setUpKeyBindings();
    }

    /**
     * Draws the end screen, including the background,
     * the end box and the text messages.
     *
     * @param g the Graphics object used to draw
     */
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        drawGameBackground(g2d);
        drawGameOverBox(g2d);
        drawTextMessages(g2d);
    }

    /**
     * Draws the background on the screen.
     *
     * @param g2d the Graphics2D object used to draw
     */
    private void drawGameBackground(Graphics2D g2d){
        gamePanel.drawGame(g2d);
    }

    /**
     * Draws the semi-transparent box that displays the end screen.
     *
     * @param g2d the Graphics2D object used to draw
     */
    private void drawGameOverBox(Graphics2D g2d){
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, TRANSPARENCY_LEVEL));
        g2d.setColor(Color.BLACK);
        g2d.fillRect(boxX, boxY, boxWidth, boxHeight);
    }

    /**
     * Draws the text messages on the screen.
     *
     * @param g2d the Graphics2D object used to draw
     */
    private void drawTextMessages(Graphics2D g2d){
        int y = boxY + LARGE_FONT_SIZE*2;

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, FULL_OPACITY));
        g2d.setColor(TEXT_COLOR);
        drawCenteredText("GAME OVER", g2d, LARGE_FONT_SIZE, y);
        drawCenteredText("FINAL SCORE: " + finalScore, g2d, SMALL_FONT_SIZE, y* 3 / 2);
        drawCenteredText("BEST SCORE: " + bestScore, g2d, SMALL_FONT_SIZE, y*2);
        drawCenteredText("Press R to PLAY AGAIN", g2d, SMALL_FONT_SIZE, y*5 / 2);
        drawCenteredText("Press ESC to QUIT", g2d, SMALL_FONT_SIZE, y* 6 / 2);
    }

    /**
     * Draws the given text centered horizontally
     * at the given y-coordinate.
     *
     * @param text The text to be drawn
     * @param g the Graphics2D object used to draw the text
     * @param fontSize The font size to be used for the text
     * @param y The y-coordinate at which to draw the text
     */
    private void drawCenteredText(String text, Graphics2D g, int fontSize, int y){
        g.setFont(new Font(FONT_NAME, Font.BOLD, fontSize));
        int textLength = (int)g.getFontMetrics().getStringBounds(text, g).getWidth();
        int xPos = (panelWidth - textLength) / 2;
        g.drawString(text, xPos, y);
    }

    /**
     * Set up key bindings for exiting och re-playing the game.
     */
    private void setUpKeyBindings(){
        Action restartGame = new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                gamePanel.restartGame();
                gameStateHandler.setGameState(GameStateHandler.GameState.GAME);
            }
        };

        InputMap inputMap = gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = gamePanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "restart");
        actionMap.put("restart", restartGame);
    }

    /**
     * Sets the final score to display on the end screen.
     *
     * @param finalScore The final score
     */
    public void setFinalScore(int finalScore){
        this.finalScore = finalScore;
    }

    /**
     * Sets the best score to display on the end screen.
     *
     * @param bestScore The best score
     */
    public void setBestScore(int bestScore){
        this.bestScore = bestScore;
    }
}
