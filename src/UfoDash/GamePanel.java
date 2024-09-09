package UfoDash;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The 'GamePanel' class is the main panel for the game, handling the drawing of the
 * game components and managing the game's state. It initializes all the game components,
 * sets up key bindings, and updates the game state during the game loop.
 */
public class GamePanel extends JPanel implements Drawable{
    private Image backgroundImage = null;
    private GameStateHandler gameStateHandler = null;
    private GameComponents gameComponents = null;
    private List<Drawable> drawables = new ArrayList<>();
    private int groundHeight, playerStartY, panelWidth, panelHeight;
    private boolean gameOver = false;


    /**
     * Constructs the game panel with specified dimensions and sets upp
     * key bindings and initialize game values.
     *
     * @param panelWidth The width of the game panel
     * @param panelHeight The height of the game panel
     */
    public GamePanel(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.playerStartY = panelHeight / 3;
        this.groundHeight = panelHeight / 8;

        setPreferredSize(new Dimension(panelWidth, panelHeight));
        initializeGame();
        setUpKeyBindings();
    }

    /**
     * Initializes the game's components and loads the background image.
     */
    private void initializeGame(){
        // Clear the drawables list to remove any leftover objects from the previous game
        drawables.clear();
        this.backgroundImage = loadImage("space.png");
        this.gameComponents = new GameComponents(panelHeight, panelWidth, groundHeight, playerStartY);
        addGameComponentsToDrawable();
        gameOver = false;
    }

    private void addGameComponentsToDrawable(){
        drawables.add(gameComponents.getPlayer());
        drawables.add(gameComponents.getAsteroidManager());
        drawables.addAll(gameComponents.getGrounds());
        drawables.add(gameComponents.getPowerUpManager());
        drawables.add(gameComponents.getEnemyManager());
        drawables.add(gameComponents.getProjectileManager());
    }

    /**
     * Loads an image from the given path.
     *
     * @param imagePath The path to the image file
     * @return the loaded image, or null if the image can't be found or loaded
     */
    private Image loadImage(String imagePath){
        Image img = null;
        try {
            URL imageURL = ClassLoader.getSystemResource("images/" + imagePath);
            if(imageURL != null){
                img = ImageIO.read(imageURL);
                if(img == null){
                    System.err.println("Failed to load image: " + imagePath);
                }
            }else {
                System.err.println("Image not found: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return img;
        }
        return img;
    }

    /**
     * Overridden method to paint all game components on the screen.
     *
     * @param g the Graphics object used for drawing
     */
    @Override protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D) g;
        if(gameStateHandler != null){
            gameStateHandler.draw(g2d); //Draws based on the current game state
        }
    }

    /**
     * Draw the game's background.
     *
     * @param g the Graphics object used to draw the object
     */
    @Override public void draw(final Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else{
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Draw all game components.
     *
     * @param g the Graphics object used to draw the object
     */
    public void drawGame(Graphics g){
        draw(g);
        gameComponents.getHeadsUpDisplay().drawHeart(g);
        gameComponents.getHeadsUpDisplay().drawScore(g);
        for (Drawable drawable : drawables){
            drawable.draw(g);
        }
    }

    /**
     * Updates the game state on each game loop iteration.
     */
    @Override public void update() {
        if(gameStateHandler != null && gameStateHandler.getCurrentState() == GameStateHandler.GameState.GAME){
            int playerLives = gameComponents.getPlayer().getLives();

            // Start asteroid, enemy and power-up-spawners if they aren't already running
            if(!gameComponents.getAsteroidManager().isSpawnerStarted()){
                gameComponents.getAsteroidManager().startSpawner();
            }
            if(!gameComponents.getEnemyManager().isSpawnerStarted()){
                gameComponents.getEnemyManager().startSpawner();
            }
            if(!gameComponents.getPowerUpManager().isSpawnerStarted() && playerLives == 1){
                gameComponents.getPowerUpManager().startSpawner();
            }else if(gameComponents.getPowerUpManager().isSpawnerStarted() && playerLives > 1){
                gameComponents.getPowerUpManager().stopSpawner();
            }

            // Update all game components
            for (Drawable drawable : drawables){
                drawable.update();
            }

            // Check collisions and game over condition
            gameComponents.getCollisionHandler().processCollisions(
                    gameComponents.getPlayer(),
                    gameComponents.getAsteroidManager().getAsteroids(),
                    gameComponents.getPowerUpManager().getPowerUps(),
                    gameComponents.getEnemyManager().getEnemies(),
                    gameComponents.getProjectileManager().getProjectiles(),
                    groundHeight, panelHeight, playerStartY);

            if(playerLives == 0){
                gameOver = true;
                gameStateHandler.setGameState(GameStateHandler.GameState.END);
            }
        }
    }

    /**
     * Restarts the game by reinitializing the game components.
     */
    public void restartGame(){
        initializeGame();
    }

    /**
     * Returns whether the game is over.
     *
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Sets the game state handler to manage different game states.
     *
     * @param gameStateHandler The game state handler to set
     */
    public void setGameStateHandler(GameStateHandler gameStateHandler) {
        this.gameStateHandler = gameStateHandler;
    }

    /**
     * Set up key bindings for handling game input such as jumping and shooting.
     */
    private void setUpKeyBindings(){
        Action jumpAction = new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                if (gameStateHandler != null && gameStateHandler.getCurrentState() == GameStateHandler.GameState.GAME) {
                    gameComponents.getPlayer().jump();
                }
            }
        };

        // Create an action to close the window
        Action closeAction = new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        };

        Action shootAction = new AbstractAction()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                if (gameStateHandler != null && gameStateHandler.getCurrentState() == GameStateHandler.GameState.GAME) {
                    gameComponents.getPlayer().shoot(gameComponents.getProjectileManager());
                }
            }
        };

        // Get input and action maps
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "jump");
        actionMap.put("jump", jumpAction);

        // Bind "ESCAPE" to closeAction
        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "close");
        actionMap.put("close", closeAction);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "shoot");
        actionMap.put("shoot", shootAction);
    }

    /**
     * Returns a reference to the player.
     *
     * @return the player object
     */
    public Player getPlayer() {
        return gameComponents.getPlayer();
    }
}
