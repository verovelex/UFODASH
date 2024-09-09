package UfoDash;


import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The 'GameLoop' class initializes and manages the main game loop of the 'UFO Dash' game.
 * It sets up the game environment, the user interface and
 * handles the continuous updating and rendering of the game components
 * at a specified frame rate.
 */
public class GameLoop {
    private static final int WIDTH = 700, HEIGHT = 800, FRAME_RATE = 60;
    private GamePanel gamePanel;

    /**
     * Constructs a new instance of 'GameLoop' which initializes and starts the game.
     */
    public GameLoop() {
        initializeGame();
        setUpUI();
        startGame();
    }

    /**
     * Initializes the game's components and state.
     * It also sets upp the game panel and game state handler.
     */
    private void initializeGame(){
        gamePanel = new GamePanel(WIDTH, HEIGHT);
        GameStateHandler gameStateHandler = new GameStateHandler(gamePanel, WIDTH, HEIGHT);
        gamePanel.setGameStateHandler(gameStateHandler);
    }

    /**
     * Sets up the user interface for the game.
     */
    private void setUpUI(){
        JFrame frame = new JFrame("UFO Dash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setVisible(true);

        // Attempt to set focus on the game panel after the window is visible
        gamePanel.requestFocusInWindow();
    }

    /**
     * Starts the main game loop, which updates and repaints the game panel
     * at a fixed interval defined by the frame rate.
     */
    private void startGame(){
        final Action gameLoop = new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                if(!gamePanel.isGameOver()){
                    gamePanel.update();
                    gamePanel.repaint();
                }
            }
        };
        Timer timer = new Timer(1000/FRAME_RATE, gameLoop);
        timer.setCoalesce(true);
        timer.start();
    }

    /**
     * The main method that starts the game by creating an instance of 'GameLoop'.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args){
        new GameLoop();
    }
}
