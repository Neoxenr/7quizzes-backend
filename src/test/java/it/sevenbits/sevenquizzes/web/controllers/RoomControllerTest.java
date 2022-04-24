package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;
import it.sevenbits.sevenquizzes.core.repository.game.GameRepository;
import it.sevenbits.sevenquizzes.core.repository.game.GameRepositoryStatic;
import it.sevenbits.sevenquizzes.core.repository.room.RoomRepository;
import it.sevenbits.sevenquizzes.core.repository.room.RoomRepositoryStatic;
import it.sevenbits.sevenquizzes.web.model.room.CreateRoomRequest;
import it.sevenbits.sevenquizzes.web.model.room.JoinRoomRequest;
import it.sevenbits.sevenquizzes.web.service.RoomService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RoomControllerTest {
    private RoomController roomController;

    private RoomService roomService;

    private RoomRepository roomRepository;
    private GameRepository gameRepository;

    @Before
    public void setUp() {
        roomRepository = new RoomRepositoryStatic(new HashMap<>(), new HashMap<>());
        gameRepository = new GameRepositoryStatic(new HashMap<>());

        roomService = new RoomService(roomRepository);

        roomController = new RoomController(roomService);
    }

    @Test
    public void getRoomsTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();
        final String roomName = "Test room";

        roomRepository.create(roomId, playerId, roomName);

        ResponseEntity<List<RoomWithOptions>> response = roomController.getRooms();

        Assert.assertEquals(1, response.getBody().size());
        Assert.assertEquals(roomId, response.getBody().get(0).getRoomId());
        Assert.assertEquals(roomName, response.getBody().get(0).getRoomName());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createRoomTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomName = "Test room";

        final CreateRoomRequest createRoomRequest = new CreateRoomRequest(playerId, roomName);

        final ResponseEntity<CreateRoomResponse> response = roomController.createRoom(createRoomRequest);

        Assert.assertEquals(1, roomRepository.getAll().size());
        Assert.assertEquals(new Player(playerId), response.getBody().getPlayers().get(0));
        Assert.assertEquals(roomName, response.getBody().getRoomName());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getRoomTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();
        final String roomName = "Test room";

        roomRepository.create(roomId, playerId, roomName);

        final ResponseEntity<GetRoomResponse> response = roomController.getRoom(roomId);

        Assert.assertEquals(roomId, Objects.requireNonNull(response.getBody()).getRoomId());
        Assert.assertEquals(1, response.getBody().getPlayers().size());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void joinRoomTest() throws SQLException {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();
        final String roomName = "Test room";

        roomRepository.create(roomId, playerId, roomName);

        final String newPlayerId = UUID.randomUUID().toString();
        final JoinRoomRequest joinRoomRequest = new JoinRoomRequest(newPlayerId);

        final ResponseEntity<Boolean> response = (ResponseEntity<Boolean>) roomController.joinRoom(roomId, joinRoomRequest);

        Assert.assertEquals(newPlayerId, roomRepository.getPlayers(roomId).get(1).getPlayerId());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
