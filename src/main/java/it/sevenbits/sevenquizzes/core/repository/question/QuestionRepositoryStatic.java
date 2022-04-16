package it.sevenbits.sevenquizzes.core.repository.question;

import it.sevenbits.sevenquizzes.core.model.question.QuestionAnswer;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptionsAndAnswer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuestionRepositoryStatic implements QuestionRepository {
    private final Map<String, QuestionWithOptionsAndAnswer> questions;
    private final Map<String, Map<String, QuestionWithOptionsAndAnswer>> roomsQuestions;

    /**
     * QuestionRepositoryStatic constructor
     */
    public QuestionRepositoryStatic(final Map<String, QuestionWithOptionsAndAnswer> questions,
            final Map<String, Map<String, QuestionWithOptionsAndAnswer>> roomsQuestions) {
        this.questions = questions;
        this.roomsQuestions = roomsQuestions;

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa1",
                new QuestionWithOptionsAndAnswer(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa1", "question text 1",
                Arrays.asList(new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa1", "answer text1"),
                new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa2", "answer text2"))),
                "3fa85f64-5717-4562-b3fc-2c963f66afa1"));

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa2",
                new QuestionWithOptionsAndAnswer(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa2", "question text 2",
                Arrays.asList(new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa2", "answer text1"),
                new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa3", "answer text2"))),
                "3fa85f64-5717-4562-b3fc-2c963f66afa2"));

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa3",
                new QuestionWithOptionsAndAnswer(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa3", "question text 3",
                Arrays.asList(new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa3", "answer text1"),
                new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa4", "answer text2"))),
                "3fa85f64-5717-4562-b3fc-2c963f66afa3"));

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa4",
                new QuestionWithOptionsAndAnswer(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa4", "question text 4",
                Arrays.asList(new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa4", "answer text1"),
                new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa5", "answer text2"))),
                "3fa85f64-5717-4562-b3fc-2c963f66afa4"));

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa5",
                new QuestionWithOptionsAndAnswer(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa5", "question text 5",
                Arrays.asList(new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa5", "answer text1"),
                new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa6", "answer text2"))),
                "3fa85f64-5717-4562-b3fc-2c963f66afa5"));
    }

    /**
     * Returns all available questions ids in questions repository
     *
     * @return Set<String> - all available questions ids in questions repository
     */
    @Override
    public List<String> getRoomQuestionsId(final String roomId) {
        return new ArrayList<>(roomsQuestions.get(roomId).keySet());
    }

    /**
     * Return correct answer id on question
     *
     * @param questionId - question id
     * @return String - correct answer id on question
     */
    @Override
    public String getCorrectAnswerId(final String questionId) {
        return questions.get(questionId).getAnswerId();
    }

    /**
     * Returns question data by id
     *
     * @param questionId - question id
     * @return QuestionWithOptions - question data
     */
    @Override
    public QuestionWithOptions getById(final String questionId) {
        return questions.get(questionId).getQuestion();
    }

    /**
     * Creates question for room
     *
     * @param roomId - room id
     * @param questionsCount - questions count
     */
    public void addRoomQuestions(final String roomId, final int questionsCount) {
        final List<String> questionsIds = new ArrayList<>(questions.keySet());

        final Map<String, QuestionWithOptionsAndAnswer> roomQuestions = new HashMap<>();

        final Random random = new Random();

        for (int i = 0; i < questionsCount; i++) {
            final int index = random.nextInt(questionsIds.size());
            final String id = questionsIds.get(index);
            roomQuestions.put(id, questions.get(id));
            questionsIds.remove(index);
        }

        roomsQuestions.put(roomId, roomQuestions);
    }
}
