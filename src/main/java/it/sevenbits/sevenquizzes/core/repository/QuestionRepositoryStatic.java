package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.QuestionAnswer;
import it.sevenbits.sevenquizzes.core.model.QuestionWithOptions;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QuestionRepositoryStatic implements QuestionRepository {
    private final Map<String, Map.Entry<QuestionWithOptions, String>> questions;

    /**
     * QuestionRepositoryStatic constructor
     */
    public QuestionRepositoryStatic() {
        questions = new HashMap<>();

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa1",
                new AbstractMap.SimpleEntry<>(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa1", "question text 1",
                Arrays.asList(new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa1", "answer text1"),
                new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa2", "answer text2"))),
                "3fa85f64-5717-4562-b3fc-2c963f66afa1"));

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa2",
                new AbstractMap.SimpleEntry<>(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa2", "question text 2",
                Arrays.asList(new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa2", "answer text1"),
                new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa3", "answer text2"))),
                "3fa85f64-5717-4562-b3fc-2c963f66afa2"));

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa3",
                new AbstractMap.SimpleEntry<>(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa3", "question text 3",
                Arrays.asList(new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa3", "answer text1"),
                new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa4", "answer text2"))),
                "3fa85f64-5717-4562-b3fc-2c963f66afa3"));

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa4",
                new AbstractMap.SimpleEntry<>(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa4", "question text 4",
                Arrays.asList(new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa4", "answer text1"),
                new QuestionAnswer("3fa85f64-5717-4562-b3fc-2c963f66afa5", "answer text2"))),
                "3fa85f64-5717-4562-b3fc-2c963f66afa5"));

        questions.put("3fa85f64-5717-4562-b3fc-2c963f66afa5",
                new AbstractMap.SimpleEntry<>(new QuestionWithOptions("3fa85f64-5717-4562-b3fc-2c963f66afa5", "question text 5",
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
    public Set<String> getQuestionsIds() {
        return questions.keySet();
    }

    /**
     * Return correct answer id on question
     *
     * @param questionId - question id
     * @return String - correct answer id on question
     */
    @Override
    public String getCorrectAnswerId(final String questionId) {
        return questions.get(questionId).getValue();
    }

    /**
     * Returns question data by id
     *
     * @param questionId - question id
     * @return QuestionWithOptions - question data
     */
    @Override
    public QuestionWithOptions getQuestionById(final String questionId) {
        return questions.get(questionId).getKey();
    }

    /**
     * Removes question from questions repository by id
     *
     * @param questionId - question id
     */
    @Override
    public void removeQuestion(final String questionId) {
        questions.remove(questionId);
    }
}
