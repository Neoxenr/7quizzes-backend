package it.sevenbits.sevenquizzes.core.model.question;

import it.sevenbits.sevenquizzes.core.model.question.QuestionAnswer;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;

import java.util.List;

public class QuestionWithOptionsAndAnswer extends QuestionWithOptions {
    private final String answerId;

    /**
     * QuestionWithOptions constructor
     *
     * @param questionId   - question id
     * @param questionText - question text
     * @param answersList  - answers list on question
     */
    public QuestionWithOptionsAndAnswer(final String questionId, final String questionText,
                                        final List<QuestionAnswer> answersList, final String answerId) {
        super(questionId, questionText, answersList);
        this.answerId = answerId;
    }

    public String getAnswerId() {
        return answerId;
    }
}
