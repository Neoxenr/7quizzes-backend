package it.sevenbits.sevenquizzes.core.model;

public class QuestionAnswer {
    private final String answerId;
    private final String answerText;

    /**
     * QuestionAnswer constructor
     *
     * @param answerId - answer id on question
     * @param answerText - answer text on question
     */
    public QuestionAnswer(final String answerId, final String answerText) {
        this.answerId = answerId;
        this.answerText = answerText;
    }

    public String getAnswerId() {
        return answerId;
    }

    public String getAnswerText() {
        return answerText;
    }
}
