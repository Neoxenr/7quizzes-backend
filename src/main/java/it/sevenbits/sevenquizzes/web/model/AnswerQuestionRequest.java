package it.sevenbits.sevenquizzes.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerQuestionRequest {
    private final String answerId;

    /**
     * Returns AnswerQuestionRequest model
     *
     * @param answerId - answer id
     */
    @JsonCreator
    public AnswerQuestionRequest(@JsonProperty("answerId") final String answerId) {
        this.answerId = answerId;
    }

    public String getAnswerId() {
        return answerId;
    }
}
