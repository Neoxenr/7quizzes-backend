package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomsResponse;
import it.sevenbits.sevenquizzes.web.model.room.CreateRoomRequest;
import it.sevenbits.sevenquizzes.web.model.room.JoinRoomRequest;
import it.sevenbits.sevenquizzes.web.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RoomController {
    private final RoomService roomService;

    public RoomController(final RoomService roomService) {
        this.roomService = roomService;
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetRoomsResponse> getRooms() {
        try {
            return new ResponseEntity<>(roomService.getRooms(), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/rooms", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody final CreateRoomRequest createRoomRequest) {
        try {
            return new ResponseEntity<>(roomService.createRoom(createRoomRequest.getPlayerId(), createRoomRequest.getRoomName()), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/rooms/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetRoomResponse> getRoom(@PathVariable final String id) {
        try {
            return new ResponseEntity<>(roomService.getRoom(id), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/rooms/{id}/join", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> joinRoom(@PathVariable final String id, @RequestBody final JoinRoomRequest joinRoomRequest) {
        try {
            roomService.joinRoom(id, joinRoomRequest.getPlayerId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NullPointerException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
