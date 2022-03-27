package it.sevenbits.sevenquizzes.core.model.game;

public class GameScore {
    private int totalScore;

    private int questionScore;

    /**
     * GameScore constructor
     */
    public GameScore() {
        totalScore = 0;
        questionScore = 0;
    }

    /**
     * Returns total game score
     *
     * @return int - total game score
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Returns total question game score
     *
     * @return int - total question game score
     */
    public int getQuestionScore() {
        return questionScore;
    }

    /**
     * Updates total score and question score
     *
     * @param questionMark - score for right answer question
     */
    public void updateScore(final int questionMark) {
        totalScore += questionMark;
        questionScore += 1;
    }

    @Override
    public String toString() {
        return "GameScore{" +
                "totalScore=" + totalScore +
                ", questionScore=" + questionScore +
                '}';
    }
}
