package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.AnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.QuestionLocation;
import it.sevenbits.sevenquizzes.core.model.QuestionWithOptions;
import it.sevenbits.sevenquizzes.web.model.AnswerQuestionRequest;
import it.sevenbits.sevenquizzes.web.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    private final GameService gameService;

    /**
     * GameController constructor
     *
     * @param gameService - game service for work with game logic
     */
    public GameController(final GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Return response body with QuestionLocation model
     *
     * @param roomId - room id in the game
     * @return ResponseEntity<QuestionLocation> - response body with QuestionLocation model
     */
    @RequestMapping(value = "/rooms/{roomId}/game/start", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<QuestionLocation> postNewGame(@PathVariable final int roomId) {
        try {
            final QuestionLocation questionLocation = gameService.startGame();

            return new ResponseEntity<>(questionLocation, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns response body with QuestionWithOptions model
     *
     * @param roomId - room id in the game
     * @param questionId - question id
     * @return ResponseEntity<QuestionWithOptions> - response body with QuestionWithOptions model
     */
    @RequestMapping(value = "/rooms/{roomId}/game/question/{questionId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<QuestionWithOptions> getQuestionData(@PathVariable final int roomId, @PathVariable final String questionId) {
        try {
            final QuestionWithOptions questionWithOptions = gameService.getQuestion(questionId);

            return new ResponseEntity<>(questionWithOptions, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Returns response body with AnswerQuestionResponse model
     *
     * @param roomId - room id in the game
     * @param questionId - question id
     * @param answerQuestionRequest - request body
     * @return ResponseEntity<AnswerQuestionResponse> - response body with AnswerQuestionResponse model
     */
    @RequestMapping(value = "/rooms/{roomId}/game/question/{questionId}/answer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AnswerQuestionResponse> postAnswer(@PathVariable final int roomId,
            @PathVariable final String questionId,
            @RequestBody final AnswerQuestionRequest answerQuestionRequest) {
        try {
            final AnswerQuestionResponse answerQuestionResponse =
                    gameService.answerQuestion(questionId, answerQuestionRequest.getAnswerId());

            return new ResponseEntity<>(answerQuestionResponse, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
