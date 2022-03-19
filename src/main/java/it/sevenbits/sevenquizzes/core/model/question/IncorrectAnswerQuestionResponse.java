package it.sevenbits.sevenquizzes.core.model.question;

public class IncorrectAnswerQuestionResponse {
    private final String questionId;
    private final int totalScore;

    public IncorrectAnswerQuestionResponse(final String questionId, final int totalScore) {
        this.questionId = questionId;
        this.totalScore = totalScore;
    }

    public String getQuestionId() {
        return questionId;
    }

    public int getTotalScore() {
        return totalScore;
    }
}
