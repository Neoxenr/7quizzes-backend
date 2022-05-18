package it.sevenbits.sevenquizzes.core.repository.question;

import it.sevenbits.sevenquizzes.core.model.question.QuestionAnswer;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

public class PostgresQuestionRepository implements QuestionRepository {
    private final JdbcOperations jdbcOperations;

    /**
     * Constructor
     *
     * @param jdbcOperations - jdbc operations
     */
    public PostgresQuestionRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<String> getRoomQuestionsId(final String roomId) {
        return jdbcOperations.query(
                "SELECT question_id FROM rooms_questions WHERE room_id = ?",
                (resultSet, i) -> resultSet.getString("question_id"),
                roomId
        );
    }

    @Override
    public QuestionWithOptions getById(final String questionId) {
        final List<QuestionAnswer> questionAnswers = jdbcOperations.query(
                "SELECT id, text FROM answer WHERE question_id = ?",
                (answerResultSet, k) -> {
                    final String answerId = answerResultSet.getString("id");
                    final String answerText = answerResultSet.getString("text");
                    return new QuestionAnswer(answerId, answerText);
                },
                questionId
        );

        return jdbcOperations.queryForObject(
                "SELECT * FROM question WHERE id = ?",
                (questionResultSet, i) -> {
                    final String rowQuestionId = questionResultSet.getString("id");
                    final String rowQuestionText = questionResultSet.getString("text");
                    return new QuestionWithOptions(rowQuestionId, rowQuestionText, questionAnswers);
                },
                questionId
        );
    }


    @Override
    public String getCorrectAnswerId(final String questionId) {
        return jdbcOperations.queryForObject(
                "SELECT id FROM answer WHERE question_id = ? AND correct = ?",
                (resultSet, i) -> resultSet.getString("id"),
                questionId, true
        );
    }

    @Override
    public void addRoomQuestions(final String roomId, final int questionsCount) {
        final List<String> questionsId = jdbcOperations.query(
                "SELECT id FROM question ORDER BY RANDOM() LIMIT ?",
                (resultSet, i) -> resultSet.getString("id"),
                questionsCount);

        // ?
        for (final String questionId : questionsId) {
            jdbcOperations.update(
                    "INSERT INTO rooms_questions (room_id, question_id) VALUES (?, ?)",
                    roomId, questionId
            );
        }
    }
}