package it.sevenbits.sevenquizzes.core.model.question;

public class AnswerQuestionResponse {
    private final String correctAnswerId;

    private final String questionId;

    private final int totalScore;

    private final int questionScore;

    /**
     * AnswerQuestionResponse constructor
     *
     * @param correctAnswerId - correct answer id on question
     * @param questionId      - current question id
     * @param totalScore      - total game score
     * @param questionScore   - total question score
     */
    public AnswerQuestionResponse(final String correctAnswerId, final String questionId,
            final int totalScore, final int questionScore) {
        this.correctAnswerId = correctAnswerId;
        this.questionId = questionId;
        this.totalScore = totalScore;
        this.questionScore = questionScore;
    }

    public String getCorrectAnswerId() {
        return correctAnswerId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getQuestionScore() {
        return questionScore;
    }
}
