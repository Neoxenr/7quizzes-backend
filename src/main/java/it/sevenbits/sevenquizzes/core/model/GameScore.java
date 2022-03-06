package it.sevenbits.sevenquizzes.core.model;

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

    public int getTotalScore() {
        return totalScore;
    }

    public int getQuestionScore() {
        return questionScore;
    }

    /**
     * Updates total score
     *
     * @param questionMark - score for right answer on question
     */
    public void updateTotalScore(final int questionMark) {
        totalScore += questionMark;
    }

    /**
     * Updates total question score
     */
    public void updateQuestionScore() {
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
