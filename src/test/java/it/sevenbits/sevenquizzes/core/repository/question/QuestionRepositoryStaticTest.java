package it.sevenbits.sevenquizzes.core.repository.question;

import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptionsAndAnswer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

import static org.mockito.Mockito.*;

public class QuestionRepositoryStaticTest {
    private QuestionRepository questionRepository;

    private Map<String, QuestionWithOptionsAndAnswer> mockQuestions;
    private Map<String, Map<String, QuestionWithOptionsAndAnswer>> mockRoomsQuestions;

    @Before
    public void setUp() {
        mockQuestions = mock(Map.class);
        mockRoomsQuestions = mock(Map.class);

        questionRepository = new QuestionRepositoryStatic(mockQuestions, mockRoomsQuestions);
    }

    @Test
    public void getRoomQuestionsIdsTest() {
        final String roomId = UUID.randomUUID().toString();

        final List<String> questionsIds = new ArrayList<>();
        questionsIds.add(UUID.randomUUID().toString());

        final Map<String, QuestionWithOptionsAndAnswer> roomQuestions = mock(Map.class);

        when(mockRoomsQuestions.get(roomId)).thenReturn(roomQuestions);
        when(roomQuestions.keySet()).thenReturn(new HashSet<>(questionsIds));

        final List<String> resultQuestionsIds = questionRepository.getRoomQuestionsId(roomId);

        verify(mockRoomsQuestions, times(1)).get(roomId);
        verify(roomQuestions, times(1)).keySet();

        Assert.assertEquals(questionsIds, resultQuestionsIds);
    }

    @Test
    public void getCorrectAnswerIdTest() {
        final String questionId = UUID.randomUUID().toString();
        final String answerId = UUID.randomUUID().toString();

        final QuestionWithOptionsAndAnswer mockQuestion = mock(QuestionWithOptionsAndAnswer.class);

        when(mockQuestions.get(questionId)).thenReturn(mockQuestion);
        when(mockQuestion.getAnswerId()).thenReturn(answerId);

        final String resultAnswerId = questionRepository.getCorrectAnswerId(questionId);

        verify(mockQuestions, times(1)).get(questionId);
        verify(mockQuestion, times(1)).getAnswerId();

        Assert.assertEquals(answerId, resultAnswerId);
    }

    @Test
    public void getQuestionByIdTest() {
        final String questionId = UUID.randomUUID().toString();

        final QuestionWithOptionsAndAnswer mockQuestionWithAnswer = mock(QuestionWithOptionsAndAnswer.class);
        final QuestionWithOptions mockQuestion = mock(QuestionWithOptions.class);

        when(mockQuestions.get(questionId)).thenReturn(mockQuestionWithAnswer);
        when(mockQuestionWithAnswer.getQuestion()).thenReturn(mockQuestion);

        final QuestionWithOptions resultQuestion = questionRepository.getById(questionId);

        verify(mockQuestions, times(1)).get(questionId);
        verify(mockQuestionWithAnswer, times(1)).getQuestion();

        Assert.assertEquals(mockQuestion, resultQuestion);
    }

    @Test
    public void createRoomQuestionsTest() throws SQLException {
        final String roomId = UUID.randomUUID().toString();

        final List<String> questionsIds = new ArrayList<>();

        questionsIds.add(UUID.randomUUID().toString());
        questionsIds.add(UUID.randomUUID().toString());

        final QuestionWithOptionsAndAnswer firstQuestion = mock(QuestionWithOptionsAndAnswer.class);
        final QuestionWithOptionsAndAnswer secondQuestion = mock(QuestionWithOptionsAndAnswer.class);

        final Map<String, QuestionWithOptionsAndAnswer> roomQuestions = new HashMap<>();

        roomQuestions.put(questionsIds.get(0), firstQuestion);
        roomQuestions.put(questionsIds.get(1), secondQuestion);

        when(mockQuestions.keySet()).thenReturn(new HashSet<>(questionsIds));
        when(mockQuestions.get(questionsIds.get(0))).thenReturn(firstQuestion);
        when(mockQuestions.get(questionsIds.get(1))).thenReturn(secondQuestion);
        when(mockRoomsQuestions.put(roomId, roomQuestions)).thenReturn(null);

        questionRepository.addRoomQuestions(roomId, 2);

        verify(mockQuestions, times(1)).keySet();
        verify(mockQuestions, times(1)).get(questionsIds.get(0));
        verify(mockQuestions, times(1)).get(questionsIds.get(1));
        verify(mockRoomsQuestions, times(1)).put(roomId, roomQuestions);
    }
}
