package it.sevenbits.sevenquizzes.core.model.question;

public class QuestionLocation {
    private final String questionId;

    /**
     * QuestionLocation constructor
     *
     * @param questionId - question id
     */
    public QuestionLocation(final String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return questionId;
    }
}
