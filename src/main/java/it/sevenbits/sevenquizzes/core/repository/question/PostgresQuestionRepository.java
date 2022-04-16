package it.sevenbits.sevenquizzes.core.repository.question;

import it.sevenbits.sevenquizzes.core.model.question.QuestionAnswer;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PostgresQuestionRepository implements QuestionRepository {
    private final JdbcOperations jdbcOperations;

    public PostgresQuestionRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<String> getRoomQuestionsId(final String roomId) {
        return jdbcOperations.queryForObject(
                "SELECT questions_id FROM room WHERE id = ?",
                (resultSet, i) -> {
                    final Array rowArray = resultSet.getArray("questions_id");
                    return Arrays.asList((String[]) rowArray.getArray());
                },
                roomId
        );
    }

    @Override
    public QuestionWithOptions getById(final String questionId) {
        final List<QuestionAnswer> questionAnswers = jdbcOperations.query(
                "SELECT id, text FROM answer WHERE question_id = ?",
                (answerResultSet, k) -> {
                    final String rowAnswerId = answerResultSet.getString("id");
                    final String rowAnswerText = answerResultSet.getString("text");
                    return new QuestionAnswer(rowAnswerId, rowAnswerText);
                },
                questionId
        );

        return jdbcOperations.queryForObject(
                "SELECT id, text FROM question WHERE id = ?",
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
    public void addRoomQuestions(final String roomId, final int questionsCount) throws SQLException {
        final List<String> questionsId = jdbcOperations.query(
                "SELECT id FROM question ORDER BY RANDOM() LIMIT ?",
                (resultSet, i) -> resultSet.getString("id"),
                questionsCount);

        jdbcOperations.update(
                "UPDATE room SET questions_id = ? WHERE id = ?",
                Objects.requireNonNull(((JdbcTemplate) jdbcOperations).getDataSource())
                        .getConnection().createArrayOf("text", questionsId.toArray()), roomId
        );
    }
}