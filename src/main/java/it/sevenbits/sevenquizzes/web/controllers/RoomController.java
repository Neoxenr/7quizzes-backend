package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;
import it.sevenbits.sevenquizzes.core.model.user.UserCredentials;
import it.sevenbits.sevenquizzes.web.model.room.CreateRoomRequest;
import it.sevenbits.sevenquizzes.web.security.AuthRoleRequired;
import it.sevenbits.sevenquizzes.web.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @return ResponseEntity<List < RoomWithOptions>> - response with all rooms
     */
    @GetMapping
    @AuthRoleRequired("USER")
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
    @PostMapping
    @AuthRoleRequired("USER")
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody final CreateRoomRequest createRoomRequest,
            final UserCredentials userCredentials) {
        try {
            return new ResponseEntity<>(roomService.createRoom(userCredentials.getUserId(),
                    createRoomRequest.getRoomName()), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns room by id
     *
     * @param id - roomId
     * @return ResponseEntity<GetRoomResponse> - response with room
     */
    @GetMapping("/{id}")
    @AuthRoleRequired("USER")
    public ResponseEntity<GetRoomResponse> getRoom(@PathVariable final String id) {
        try {
            final GetRoomResponse response = roomService.getRoom(id);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Return HTTP status code
     *
     * @param id              - room id
     * @return ResponseEntity<?> - response with http status code
     */
    @PostMapping("/{id}/join")
    @AuthRoleRequired("USER")
    public ResponseEntity<?> joinRoom(@PathVariable final String id, final UserCredentials userCredentials) {
        try {
            roomService.joinRoom(id, userCredentials.getUserId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
