package it.sevenbits.sevenquizzes.core.model.game;

public class GameStatus {
    private String status;
    private String questionId;
    private int questionNumber;
    private final int questionsCount;

    /**
     * GameStatus constructor
     *
     * @param status - game status
     * @param questionId - question id
     * @param questionNumber - question number
     * @param questionsCount - questions count
     */
    public GameStatus(final String status, final String questionId, final int questionNumber, final int questionsCount) {
        this.status = status;
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.questionsCount = questionsCount;
    }

    /**
     * Returns game status
     *
     * @return String - status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns current question id
     *
     * @return String - current question id
     */
    public String getQuestionId() {
        return questionId;
    }

    /**
     * Returns question number
     *
     * @return int - question number
     */
    public int getQuestionNumber() {
        return questionNumber;
    }

    /**
     * Returns questions count
     *
     * @return int - questions count
     */
    public int getQuestionsCount() {
        return questionsCount;
    }

    /**
     * Setting game status
     *
     * @param status - game status
     */
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Setting question id
     *
     * @param questionId - question id
     */
    public void setQuestionId(final String questionId) {
        this.questionId = questionId;
    }

    /**
     * Updates question number
     */
    public void updateQuestionNumber() {
        questionNumber += 1;
    }
}
