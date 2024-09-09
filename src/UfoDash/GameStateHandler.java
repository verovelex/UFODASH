package UfoDash;

import java.awt.*;

/**
 * The 'GameStateHandler' class manages the different states in the game,
 * such as title screen, the game itself and end screen.
 * It also handles the rendering and updates based on the current state.
 */
public class GameStateHandler {
    private GamePanel gamePanel;
    private GameState currentState;
    private TitleState titleState;
    private EndState endState;

    /**
     * Constructs a 'GameStateHandler' object with the given game state handler,
     * game panel and panel dimensions.
     * It initializes the states for title and end screens.
     *
     * @param gamePanel The game panel screen where the title screen will be rendered
     * @param panelWidth The width of the game panel
     * @param panelHeight The height of the game panel
     */
    public GameStateHandler(GamePanel gamePanel, int panelWidth, int panelHeight) {
        this.gamePanel = gamePanel;
        currentState = GameState.TITLE;
        titleState = new TitleState(this, gamePanel, panelWidth, panelHeight);
        endState = new EndState(this, gamePanel, panelWidth, panelHeight);
    }

    /**
     * Draws the current game state on the screen.
     * The content to be rendered depends on the current state
     * of the game.
     *
     * @param g the Graphics object used to draw
     */
    public void draw(Graphics g) {
        switch (currentState) {
            case TITLE:
                titleState.draw(g);
                titleState.update();
                break;
            case GAME:
                gamePanel.drawGame(g);
                break;
            case END:
                endState.draw(g);
                break;

        }
    }

    /**
     * Sets the game to a new state.
     * Updates the end state with the final and best score.
     *
     * @param state The new state to set for the game
     */
    public void setGameState(GameState state){
        this.currentState = state;
        if(state == GameState.END){
            endState.setFinalScore(gamePanel.getPlayer().getCurrentScore());
            endState.setBestScore(gamePanel.getPlayer().getBestScore());
        }
    }

    /**
     * Returns the current state of the game.
     *
     * @return the current game state
     */
    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Enumeration defining the possible states of the game.
     */
    public enum GameState {
        TITLE, GAME, END
    }
}
