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

    public int getTotalScore() {
        return totalScore;
    }

    public int getQuestionScore() {
        return questionScore;
    }

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
