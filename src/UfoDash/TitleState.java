package UfoDash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * The 'TitleState' class represents the title screen of the game.
 * It handles the rendering of the background and the text messages,
 * as well as the key binding for starting the game.
 */
public class TitleState extends AbstractDrawable{
    private Image titleBGImage;
    private GameStateHandler gameStateHandler;
    private GamePanel gamePanel;
    private int backgroundOffset;
    private Dimension panelSize;


    private static final Color TEXT_COLOR = new Color(255, 185, 0);
    private static final Color TEXT_SHADOW_COLOR = new Color(255, 120, 0);
    private static final String FONT_NAME = "Comic Sans MS";
    private static final int LARGE_FONT_SIZE = 100;
    private static final int SMALL_FONT_SIZE = 40;
    private static final int BACKGROUND_SPEED = 2;
    private static final int LARGE_FONT_Y_OFFSET = 5;
    private static final int SMALL_FONT_Y_OFFSET = 3;


    /**
     * Constructs a 'TitleState' object with the given game state handler,
     * game panel and panel dimensions.
     *
     * @param gameStateHandler The handler that manages the current game state
     * @param gamePanel The game panel screen where the title screen will be rendered
     * @param panelWidth The width of the game panel
     * @param panelHeight The height of the game panel
     */
    public TitleState(GameStateHandler gameStateHandler, GamePanel gamePanel, int panelWidth, int panelHeight) {
        this.gameStateHandler = gameStateHandler;
        this.gamePanel = gamePanel;
        this.panelSize = new Dimension(panelWidth, panelHeight);
        this.backgroundOffset = 0;
        this.titleBGImage = loadImage("titleBG.png");
        setUpKeyBindings();
    }


    /**
     * Draws the title screen, including the background and the text messages.
     *
     * @param g the Graphics object used to draw
     */
    @Override public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        drawBackground(g2d);
        drawTextMessages(g2d);
    }

    /**
     * Draws the background image.
     * If the image is not loaded, a black rectangle is drawn instead.
     *
     * @param g the Graphics object used to draw
     */
    private void drawBackground(Graphics g){
        if(titleBGImage != null){
            g.drawImage(titleBGImage, backgroundOffset, 0, panelSize.width, panelSize.height, null);
            g.drawImage(titleBGImage, backgroundOffset + panelSize.width, 0, panelSize.width, panelSize.height, null);
        }else{
            g.setColor(Color.BLACK);
            g.fillRect(backgroundOffset, 0, panelSize.width, panelSize.height);
            g.fillRect(backgroundOffset + panelSize.width, 0, panelSize.width, panelSize.height);
        }
    }

    /**
     * Draws the text messages on the screen.
     *
     * @param g2d the Graphics2D object used to draw
     */
    private void drawTextMessages(Graphics2D g2d){
        int y = LARGE_FONT_SIZE*2;

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        g2d.setColor(TEXT_SHADOW_COLOR);
        drawCenteredText( "UFO DASH", g2d, LARGE_FONT_SIZE, y + LARGE_FONT_Y_OFFSET);
        drawCenteredText("Press SPACE to START", g2d, SMALL_FONT_SIZE, y*2 + SMALL_FONT_Y_OFFSET);
        drawCenteredText("Press ESC to QUIT", g2d, SMALL_FONT_SIZE, y* 5 / 2 + SMALL_FONT_Y_OFFSET);

        g2d.setColor(TEXT_COLOR);
        drawCenteredText( "UFO DASH", g2d, LARGE_FONT_SIZE, y);
        drawCenteredText("Press SPACE to START", g2d, SMALL_FONT_SIZE, y*2);
        drawCenteredText("Press ESC to QUIT", g2d, SMALL_FONT_SIZE, y* 5 / 2);
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
        int xPos = (panelSize.width - textLength) / 2;
        g.drawString(text, xPos, y);
    }

    /**
     * Updates the background's position to create a scrolling effect.
     */
    @Override public void update() {
        backgroundOffset -= BACKGROUND_SPEED;

        if(backgroundOffset <= -panelSize.width){
            backgroundOffset = 0;
        }
    }

    /**
     * Set up key bindings for exiting och starting the game.
     */
    private void setUpKeyBindings(){
        Action startGameAction = new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                gameStateHandler.setGameState(GameStateHandler.GameState.GAME);
            }
        };

        InputMap inputMap = gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = gamePanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "start");
        actionMap.put("start", startGameAction);
    }
}
