package it.sevenbits.sevenquizzes.core.model.question;

public class IncorrectAnswerQuestionResponse {
    private final String questionId;
    private final int totalScore;

    /**
     * IncorrectAnswerQuestionResponse constructor
     *
     * @param questionId - question id
     * @param totalScore - total score
     */
    public IncorrectAnswerQuestionResponse(final String questionId, final int totalScore) {
        this.questionId = questionId;
        this.totalScore = totalScore;
    }

    /**
     * Returns question id
     *
     * @return String - question id
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * Returns total game score
     *
     * @return int - total score
     */
    public int getTotalScore() {
        return totalScore;
    }
}
