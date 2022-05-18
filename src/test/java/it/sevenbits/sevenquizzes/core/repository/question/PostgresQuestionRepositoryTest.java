package it.sevenbits.sevenquizzes.core.repository.question;

import it.sevenbits.sevenquizzes.core.model.question.QuestionAnswer;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class PostgresQuestionRepositoryTest {
    private QuestionRepository questionRepository;

    private JdbcOperations mockJdbcOperations;

    @Before
    public void setUp() {
        mockJdbcOperations = mock(JdbcTemplate.class);

        questionRepository = new PostgresQuestionRepository(mockJdbcOperations);
    }

    @Test
    public void getRoomQuestionsIdTest() {
        final String roomId = UUID.randomUUID().toString();
        final String query = "SELECT question_id FROM rooms_questions WHERE room_id = ?";

        final List<String> mockQuestionsId = mock(List.class);

        when(mockJdbcOperations.query(eq(query), any(RowMapper.class), eq(roomId))).
                thenReturn(mockQuestionsId);

        final List<String> questionsId = questionRepository.getRoomQuestionsId(roomId);

        verify(mockJdbcOperations, times(1)).
                query(eq(query), any(RowMapper.class), eq(roomId));

        Assert.assertEquals(mockQuestionsId, questionsId);
    }

    @Test
    public void getByIdTest() {
        final String questionId = UUID.randomUUID().toString();

        final String firstSqlQuery = "SELECT id, text FROM answer WHERE question_id = ?";
        final String secondSqlQuery = "SELECT * FROM question WHERE id = ?";

        final List<QuestionAnswer> mockQuestionAnswers = mock(List.class);

        final QuestionWithOptions mockQuestion = mock(QuestionWithOptions.class);

        when(mockJdbcOperations.query(eq(firstSqlQuery), any(RowMapper.class), eq(questionId))).
                thenReturn(mockQuestionAnswers);
        when(mockJdbcOperations.queryForObject(eq(secondSqlQuery), any(RowMapper.class), eq(questionId))).
                thenReturn(mockQuestion);

        final QuestionWithOptions question = questionRepository.getById(questionId);

        verify(mockJdbcOperations, times(1)).
                query(eq(firstSqlQuery), any(RowMapper.class), eq(questionId));
        verify(mockJdbcOperations, times(1)).
                queryForObject(eq(secondSqlQuery), any(RowMapper.class), eq(questionId));

        Assert.assertEquals(mockQuestion, question);
    }

    @Test
    public void getCorrectAnswerIdTest() {
        final String questionId = UUID.randomUUID().toString();
        final String correctAnswerId = UUID.randomUUID().toString();

        final String sqlQuery = "SELECT id FROM answer WHERE question_id = ? AND correct = ?";

        when(mockJdbcOperations.queryForObject(eq(sqlQuery), any(RowMapper.class), eq(questionId), eq(true))).thenReturn(correctAnswerId);

        final String correctAnswerIdResult = questionRepository.getCorrectAnswerId(questionId);

        verify(mockJdbcOperations, times(1)).queryForObject(eq(sqlQuery), any(RowMapper.class), eq(questionId), eq(true));

        Assert.assertEquals(correctAnswerId, correctAnswerIdResult);
    }

    @Test
    public void addRoomQuestionsTest() throws SQLException {
        final String roomId = UUID.randomUUID().toString();

        final int questionsCount = 1;

        final String firstSqlQuery = "SELECT id FROM question ORDER BY RANDOM() LIMIT ?";
        final String secondSqlQuery = "INSERT INTO rooms_questions (room_id, question_id) VALUES (?, ?)";

        final List<String> questionsId = new ArrayList<>();
        questionsId.add(UUID.randomUUID().toString());

        when(mockJdbcOperations.query(eq(firstSqlQuery), any(RowMapper.class), eq(questionsCount))).
                thenReturn(questionsId);
        when(mockJdbcOperations.update(eq(secondSqlQuery), eq(roomId), eq(questionsId.get(0)))).thenReturn(1);

        questionRepository.addRoomQuestions(roomId, questionsCount);

        verify(mockJdbcOperations, times(1)).query(eq(firstSqlQuery), any(RowMapper.class), eq(questionsCount));
        verify(mockJdbcOperations, times(1)).update(eq(secondSqlQuery), eq(roomId), eq(questionsId.get(0)));
    }
}
