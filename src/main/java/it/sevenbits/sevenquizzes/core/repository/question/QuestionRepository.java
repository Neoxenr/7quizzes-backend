package it.sevenbits.sevenquizzes.core.repository.question;

import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;

import java.sql.SQLException;
import java.util.List;

public interface QuestionRepository {
    /**
     * Return all available questions ids in questions repository
     *
     * @return List<String> - all available questions ids in questions repository
     */
    List<String> getRoomQuestionsId(String roomId);

    /**
     * Return question data by id
     *
     * @param questionId - question id
     * @return QuestionWithOptions - question data
     */
    QuestionWithOptions getById(String questionId);

    /**
     * Return correct answer id on question
     *
     * @param questionId - question id
     * @return String - correct answer id on question
     */
    String getCorrectAnswerId(String questionId);

    /**
     * Create questions for room
     *
     * @param roomId - room id
     * @param questionsCount - questions count
     */
    void addRoomQuestions(String roomId, int questionsCount) throws SQLException;
}
