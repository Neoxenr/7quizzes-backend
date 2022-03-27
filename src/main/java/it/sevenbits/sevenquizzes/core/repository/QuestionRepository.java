package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;

import java.util.List;

public interface QuestionRepository {
    /**
     * Returns all available questions ids in questions repository
     *
     * @return List<String> - all available questions ids in questions repository
     */
    List<String> getRoomQuestionsIds(String roomId);

    /**
     * Return correct answer id on question
     *
     * @param questionId - question id
     * @return String - correct answer id on question
     */
    String getCorrectAnswerId(String roomId, String questionId);

    /**
     * Returns question data by id
     *
     * @param questionId - question id
     * @return QuestionWithOptions - question data
     */
    QuestionWithOptions getRoomQuestionById(String roomId, String questionId);

    /**
     * Removes question from questions repository by id
     *
     * @param questionId - question id
     */
    void removeRoomQuestion(String roomId, String questionId);

    /**
     * Creates questions for room
     *
     * @param roomId - room id
     * @param questionsCount - questions count
     */
    void createRoomQuestions(String roomId, int questionsCount);
}
