package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.game.GameStatus;
import it.sevenbits.sevenquizzes.core.model.question.AnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.question.IncorrectAnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.question.QuestionLocation;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.model.user.UserCredentials;
import it.sevenbits.sevenquizzes.web.model.question.AnswerQuestionRequest;
import it.sevenbits.sevenquizzes.web.security.AuthRoleRequired;
import it.sevenbits.sevenquizzes.web.service.game.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
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
    @PostMapping("/{roomId}/game/start")
    @AuthRoleRequired("USER")
    public ResponseEntity<QuestionLocation> postNewGame(@PathVariable final String roomId,
            final UserCredentials userCredentials) {
        try {
            final QuestionLocation questionLocation = gameService.startGame(roomId, userCredentials.getUserId());

            return new ResponseEntity<>(questionLocation, HttpStatus.OK);
        } catch (NullPointerException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns response body with QuestionWithOptions model
     *
     * @param roomId     - room id in the game
     * @param questionId - question id
     * @return ResponseEntity<QuestionWithOptions> - response body with QuestionWithOptions model
     */
    @GetMapping("/{roomId}/game/question/{questionId}")
    @AuthRoleRequired("USER")
    public ResponseEntity<QuestionWithOptions> getQuestionData(@PathVariable final String roomId,
            @PathVariable final String questionId) {
        try {
            final QuestionWithOptions questionWithOptions = gameService.getQuestionData(roomId, questionId);

            return new ResponseEntity<>(questionWithOptions, HttpStatus.OK);
        } catch (NullPointerException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns response body with AnswerQuestionResponse model
     *
     * @param roomId                - room id in the game
     * @param questionId            - question id
     * @param answerQuestionRequest - request body
     * @return ResponseEntity<AnswerQuestionResponse> - response body with AnswerQuestionResponse model
     */
    @PostMapping("{roomId}/game/question/{questionId}/answer")
    @AuthRoleRequired("USER")
    public ResponseEntity<?> postAnswer(@PathVariable final String roomId,
            @PathVariable final String questionId,
            @RequestBody final AnswerQuestionRequest answerQuestionRequest,
            final UserCredentials userCredentials) {
        try {
            final AnswerQuestionResponse answerQuestionResponse =
                    gameService.answerQuestion(roomId, userCredentials.getUserId(),
                    questionId, answerQuestionRequest.getAnswerId());
            if (answerQuestionResponse.getCorrectAnswerId() == null) {
                return new ResponseEntity<>(new IncorrectAnswerQuestionResponse(questionId,
                        answerQuestionResponse.getTotalScore()), HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(answerQuestionResponse, HttpStatus.OK);
        } catch (NullPointerException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Return game status
     *
     * @param roomId - room id
     * @return ResponseEntity<GameStatus> - game status
     */
    @GetMapping("/{roomId}/game")
    @AuthRoleRequired("USER")
    public ResponseEntity<GameStatus> getGameStatus(@PathVariable final String roomId) {
        try {
            return new ResponseEntity<>(gameService.getGameStatus(roomId), HttpStatus.OK);
        } catch (NullPointerException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
