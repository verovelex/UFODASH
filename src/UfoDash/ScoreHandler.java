package UfoDash;

import java.io.*;

/**
 * The 'ScoreHandler' class manages the current score and the best score in the game.
 * It handles score incrementing, and saving/loading the best score to/from a file.
 */
public class ScoreHandler {
    private static final int SCORE_DIVISOR = 2; // Adjust the displayed score by dividing it
    private int currentScore = 0;
    private DataStorage dataStorage = new DataStorage();

    private static final String SCORE_FILE = "bestScore.dat";

    /**
     * Constructs a 'ScoreHandler' object that loads the best score from the file.
     */
    public ScoreHandler() {
        this.dataStorage.setBestScore(loadBestScore());
    }

    /**
     * Increments the current score. If the new score is higher than the best score,
     * then the best score updates and gets saved to the file.
     */
    public void increaseScore(){
        currentScore++;
        if(currentScore > dataStorage.getBestScore()){
            dataStorage.setBestScore(currentScore);
            saveBestScore();
        }
    }

    /**
     * Returns the current score, adjusted by dividing it.
     *
     * @return the adjusted current score
     */
    public int getCurrentScore(){
        return currentScore/SCORE_DIVISOR;
    }

    /**
     * Returns the best score, adjusted by dividing it.
     *
     * @return the adjusted best score
     */
    public int getBestScore(){
        return dataStorage.getBestScore()/SCORE_DIVISOR;
    }

    /**
     * Saves the best score to a file.
     * The file is created if it doesn't exist
     */
    private void saveBestScore(){
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(getScoreFilePath()))) {
            objectOutputStream.writeObject(dataStorage);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save best score in the file: " + e.getMessage());
        }
    }

    /**
     * Loads the best score from a file.
     * If the file doesn't exist or is empty, initializes
     * the best score to 0 and creates the file.
     *
     * @return the best score loaded from the file
     */
    private int loadBestScore(){
        File file = new File(getScoreFilePath());
        if(!file.exists() || file.length() == 0){
            saveBestScore();
            return 0;
        }

        try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            DataStorage loadedData = (DataStorage)objectInputStream.readObject();
            return loadedData.getBestScore();
        } catch (IOException e){
            System.err.println("Failed to load best score file: " + e.getMessage());
            return 0;
        } catch (ClassNotFoundException e){
            System.err.println("Class not found while loading best score file: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Gets the file path for storing the best score based on the user's
     * home directory.
     *
     * @return the file path to the score file
     */
    private String getScoreFilePath(){
        String userHome = System.getProperty("user.home");
        return userHome + File.separator + SCORE_FILE;
    }
}
