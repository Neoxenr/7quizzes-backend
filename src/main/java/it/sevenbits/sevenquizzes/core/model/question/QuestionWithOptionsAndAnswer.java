package it.sevenbits.sevenquizzes.core.model.question;

public class QuestionWithOptionsAndAnswer {
    private final QuestionWithOptions question;

    private final String answerId;

    /**
     * QuestionWithOptions constructor
     *
     * @param questionId   - question id
     * @param questionText - question text
     * @param answersList  - answers list on question
     */
    public QuestionWithOptionsAndAnswer(final QuestionWithOptions question, final String answerId) {
        this.question = question;
        this.answerId = answerId;
    }

    public QuestionWithOptions getQuestion() {
        return question;
    }

    public String getAnswerId() {
        return answerId;
    }
}
