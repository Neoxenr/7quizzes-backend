package it.sevenbits.sevenquizzes.core.model.game;

public class GameStatus {
    private String status;
    private String questionId;
    private int questionNumber;
    private final int questionsCount;

    public GameStatus(final String status, final String questionId, final int questionNumber, final int questionsCount) {
        this.status = status;
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.questionsCount = questionsCount;
    }

    public String getStatus() {
        return status;
    }

    public String getQuestionId() {
        return questionId;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }


    public void setStatus(final String status) {
        this.status = status;
    }

    public void setQuestionId(final String questionId) {
        this.questionId = questionId;
    }

    public void updateQuestionNumber() {
        questionNumber += 1;
    }
}
