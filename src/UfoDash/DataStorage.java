package UfoDash;

import java.io.Serializable;

/**
 * The 'DataStorage' class is used to store serializable data,
 * such as game scores.
 */
public class DataStorage implements Serializable {
    private int bestScore;

    /**
     * Returns the best score stored in this DataStorage object.
     *
     * @return the best score
     */
    public int getBestScore(){
        return bestScore;
    }

    /**
     * Sets the best score in this DataStorage object.
     *
     * @param bestScore the new best score to be stored
     */
    public void setBestScore(int bestScore){
        this.bestScore = bestScore;
    }
}
