package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptionsAndAnswer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

        final List<String> resultQuestionsIds = questionRepository.getRoomQuestionsIds(roomId);

        verify(mockRoomsQuestions, times(1)).get(roomId);
        verify(roomQuestions, times(1)).keySet();

        Assert.assertEquals(questionsIds, resultQuestionsIds);
    }

    @Test
    public void getCorrectAnswerIdTest() {
        final String roomId = UUID.randomUUID().toString();
        final String questionId = UUID.randomUUID().toString();
        final String answerId = UUID.randomUUID().toString();

        final Map<String, QuestionWithOptionsAndAnswer> mockRoomQuestions = mock(Map.class);
        final QuestionWithOptionsAndAnswer mockQuestion = mock(QuestionWithOptionsAndAnswer.class);

        when(mockRoomsQuestions.get(roomId)).thenReturn(mockRoomQuestions);
        when(mockRoomQuestions.get(questionId)).thenReturn(mockQuestion);
        when(mockQuestion.getAnswerId()).thenReturn(answerId);

        final String resultAnswerId = questionRepository.getCorrectAnswerId(roomId, questionId);

        verify(mockRoomsQuestions, times(1)).get(roomId);
        verify(mockRoomQuestions, times(1)).get(questionId);
        verify(mockQuestion, times(1)).getAnswerId();

        Assert.assertEquals(answerId, resultAnswerId);
    }

    @Test
    public void getRoomQuestionById() {
        final String roomId = UUID.randomUUID().toString();
        final String questionId = UUID.randomUUID().toString();

        final QuestionWithOptionsAndAnswer mockQuestionWithAnswer = mock(QuestionWithOptionsAndAnswer.class);
        final Map<String, QuestionWithOptionsAndAnswer> mockRoomQuestions = mock(Map.class);

        final QuestionWithOptions question = mock(QuestionWithOptions.class);

        when(mockRoomsQuestions.get(roomId)).thenReturn(mockRoomQuestions);
        when(mockRoomQuestions.get(questionId)).thenReturn(mockQuestionWithAnswer);
        when(mockQuestionWithAnswer.getQuestion()).thenReturn(question);

        final QuestionWithOptions resultQuestion = questionRepository.getRoomQuestionById(roomId, questionId);

        verify(mockRoomsQuestions, times(1)).get(roomId);
        verify(mockRoomQuestions, times(1)).get(questionId);
        verify(mockQuestionWithAnswer, times(1)).getQuestion();

        Assert.assertEquals(question, resultQuestion);
    }

    @Test
    public void createRoomQuestionsTest() {
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

        questionRepository.createRoomQuestions(roomId, 2);

        verify(mockQuestions, times(1)).keySet();
        verify(mockQuestions, times(1)).get(questionsIds.get(0));
        verify(mockQuestions, times(1)).get(questionsIds.get(1));
        verify(mockRoomsQuestions, times(1)).put(roomId, roomQuestions);
    }

    @Test
    public void removeRoomQuestionTest() {
        final String roomId = UUID.randomUUID().toString();
        final String questionId = UUID.randomUUID().toString();

        final QuestionWithOptionsAndAnswer question = mock(QuestionWithOptionsAndAnswer.class);

        final Map<String, QuestionWithOptionsAndAnswer> roomQuestions = new HashMap<>();
        roomQuestions.put(questionId, question);

        when(mockRoomsQuestions.get(roomId)).thenReturn(roomQuestions);

        questionRepository.removeRoomQuestion(roomId, questionId);

        verify(mockRoomsQuestions, times(1)).get(roomId);

        Assert.assertEquals(roomQuestions.size(), 0);
    }
}
