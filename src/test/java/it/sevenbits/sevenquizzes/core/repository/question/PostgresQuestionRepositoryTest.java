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
        final String sqlQuery = "SELECT questions_id FROM room WHERE id = ?";

        final List<String> mockQuestionsId = mock(List.class);

        when(mockJdbcOperations.queryForObject(eq(sqlQuery), any(RowMapper.class), eq(roomId))).
                thenReturn(mockQuestionsId);

        final List<String> questionsId = questionRepository.getRoomQuestionsId(roomId);

        verify(mockJdbcOperations, times(1)).queryForObject(eq(sqlQuery), any(RowMapper.class), eq(roomId));

        Assert.assertEquals(mockQuestionsId, questionsId);
    }

    @Test
    public void getByIdTest() {
        final String questionId = UUID.randomUUID().toString();

        final String firstSqlQuery = "SELECT id, text FROM question WHERE id = ?";
        final String secondSqlQuery = "SELECT id, text FROM answer WHERE question_id = ?";

        final QuestionWithOptions mockQuestion = mock(QuestionWithOptions.class);
        final List<QuestionAnswer> mockQuestionAnswers = mock(List.class);

        when(mockJdbcOperations.query(eq(secondSqlQuery), any(RowMapper.class), eq(questionId))).
                thenReturn(mockQuestionAnswers);
        when(mockJdbcOperations.queryForObject(eq(firstSqlQuery), any(RowMapper.class), eq(questionId))).
                thenReturn(mockQuestion);

        final QuestionWithOptions question = questionRepository.getById(questionId);

        verify(mockJdbcOperations, times(1)).query(eq(secondSqlQuery), any(RowMapper.class), eq(questionId));
        verify(mockJdbcOperations, times(1)).queryForObject(eq(firstSqlQuery), any(RowMapper.class), eq(questionId));

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

        final int questionsCount = 5;

        final String firstSqlQuery = "SELECT id FROM question ORDER BY RANDOM() LIMIT ?";
        final String secondSqlQuery = "UPDATE room SET questions_id = ? WHERE id = ?";

        final List<String> mockQuestionsId = mock(List.class);
        final DataSource mockDataSource = mock(DataSource.class);
        final Connection mockConnection = mock(Connection.class);
        final Array mockArray = mock(Array.class);

        final Object[] array = new Object[]{};

        when(mockJdbcOperations.query(eq(firstSqlQuery), any(RowMapper.class), eq(questionsCount))).thenReturn(mockQuestionsId);
        when(mockJdbcOperations.update(eq(secondSqlQuery), any(Array.class), eq(roomId))).thenReturn(1);
        when(((JdbcTemplate) mockJdbcOperations).getDataSource()).thenReturn(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createArrayOf(eq("text"), any(Object[].class))).thenReturn(mockArray);
        when(mockQuestionsId.toArray()).thenReturn(array);

        questionRepository.addRoomQuestions(roomId, questionsCount);

        verify(mockJdbcOperations, times(1)).query(eq(firstSqlQuery), any(RowMapper.class), eq(questionsCount));
        verify(mockJdbcOperations, times(1)).update(eq(secondSqlQuery), any(Array.class), eq(roomId));
        verify((JdbcTemplate) mockJdbcOperations, times(1)).getDataSource();
        verify(mockDataSource, times(1)).getConnection();
        verify(mockConnection, times(1)).createArrayOf(eq("text"), any(Object[].class));
        verify(mockQuestionsId, times(1)).toArray();
    }
}
