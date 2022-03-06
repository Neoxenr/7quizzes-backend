package it.sevenbits.sevenquizzes.core.model;

import java.util.List;

public class QuestionWithOptions {
    private final String questionId;

    private final String questionText;

    private final List<QuestionAnswer> answersList;

    /**
     * QuestionWithOptions constructor
     *
     * @param questionId - question id
     * @param questionText - question text
     * @param answersList - answers list on question
     */
    public QuestionWithOptions(final String questionId, final String questionText, final List<QuestionAnswer> answersList) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answersList = answersList;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<QuestionAnswer> getAnswersList() {
        return answersList;
    }
}
