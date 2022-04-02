package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;
import it.sevenbits.sevenquizzes.web.model.room.CreateRoomRequest;
import it.sevenbits.sevenquizzes.web.model.room.JoinRoomRequest;
import it.sevenbits.sevenquizzes.web.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for room resource
 */
@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    /**
     * RoomController constructor
     *
     * @param roomService - room service
     */
    public RoomController(final RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Get all rooms
     *
     * @return ResponseEntity<List<RoomWithOptions>> - response with all rooms
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<RoomWithOptions>> getRooms() {
        try {
            return new ResponseEntity<>(roomService.getRooms().getRooms(), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Creates room
     *
     * @param createRoomRequest - createRoomRequest model
     * @return ResponseEntity<CreateRoomResponse> - response with createRoomRequest model
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody final CreateRoomRequest createRoomRequest) {
        try {
            return new ResponseEntity<>(roomService.createRoom(createRoomRequest.getPlayerId(),
                    createRoomRequest.getRoomName()), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns room by id
     *
     * @param roomId - roomId
     * @return ResponseEntity<GetRoomResponse> - response with room
     */
    @RequestMapping(value = "/{roomId}", method = RequestMethod.GET)
    public ResponseEntity<GetRoomResponse> getRoom(@PathVariable final String roomId) {
        try {
            final GetRoomResponse response = roomService.getRoom(roomId);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Return HTTP status code
     *
     * @param roomId - room id
     * @param joinRoomRequest - joinRoomRequest model
     * @return ResponseEntity<?> - response with http status code
     */
    @RequestMapping(value = "/{roomId}/join", method = RequestMethod.POST)
    public ResponseEntity<?> joinRoom(@PathVariable final String roomId, @RequestBody final JoinRoomRequest joinRoomRequest) {
        try {
            roomService.joinRoom(roomId, joinRoomRequest.getPlayerId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
