package it.sevenbits.sevenquizzes.core.model;

public class AnswerQuestionResponse {
    private final String correctAnswerId;

    private final String nextQuestionId;

    private final int totalScore;

    private final int questionScore;

    /**
     * AnswerQuestionResponse constructor
     *
     * @param correctAnswerId - correct answer id on question
     * @param nextQuestionId - next question id
     * @param totalScore - total game score
     * @param questionScore - total question score
     */
    public AnswerQuestionResponse(final String correctAnswerId, final String nextQuestionId,
            final int totalScore, final int questionScore) {
        this.correctAnswerId = correctAnswerId;
        this.nextQuestionId = nextQuestionId;
        this.totalScore = totalScore;
        this.questionScore = questionScore;
    }

    public String getCorrectAnswerId() {
        return correctAnswerId;
    }

    public String getNextQuestionId() {
        return nextQuestionId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getQuestionScore() {
        return questionScore;
    }
}
