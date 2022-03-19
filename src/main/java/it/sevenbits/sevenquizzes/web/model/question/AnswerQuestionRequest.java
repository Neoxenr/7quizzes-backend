package it.sevenbits.sevenquizzes.web.model.question;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerQuestionRequest {
    private final String playerId;
    private final String answerId;

    /**
     * Returns AnswerQuestionRequest model
     *
     * @param answerId - answer id
     */
    @JsonCreator
    public AnswerQuestionRequest(@JsonProperty("playerId") final String playerId, @JsonProperty("answerId") final String answerId) {
        this.playerId = playerId;
        this.answerId = answerId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
