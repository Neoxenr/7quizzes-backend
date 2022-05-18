package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomsResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;
import it.sevenbits.sevenquizzes.core.repository.room.RoomRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class RoomServiceTest {
    private RoomService roomService;

    private RoomRepository roomRepository;

    @Before
    public void setUp() {
        roomRepository = mock(RoomRepository.class);

        roomService = new RoomService(roomRepository);
    }

    @Test
    public void getRoomsTest() {
        final List<RoomWithOptions> rooms = new ArrayList<>();

        final String ownerId = UUID.randomUUID().toString();

        final RoomWithOptions firstRoom = new RoomWithOptions(UUID.randomUUID().toString(), "room 1", ownerId);
        final RoomWithOptions secondRoom = new RoomWithOptions(UUID.randomUUID().toString(), "room 2", ownerId);

        rooms.add(firstRoom);
        rooms.add(secondRoom);

        when(roomRepository.getAll()).thenReturn(rooms);

        final GetRoomsResponse resultRooms = roomService.getRooms();

        verify(roomRepository, times(1)).getAll();

        Assert.assertEquals(new GetRoomsResponse(rooms).getRooms(), resultRooms.getRooms());
    }

    @Test
    public void createRoomTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomName = "Test room";
        final String roomId = UUID.randomUUID().toString();

        final List<Player> players = new ArrayList<>();
        players.add(new Player(playerId));

        final CreateRoomResponse room = new CreateRoomResponse(roomId, roomName, playerId, players);

        when(roomRepository.create(anyString(), eq(playerId), eq(roomName))).thenReturn(room);

        final CreateRoomResponse resultRoom = roomService.createRoom(playerId, roomName);

        verify(roomRepository, times(1)).create(anyString(), eq(playerId), eq(roomName));

        Assert.assertEquals(room.getRoomName(), resultRoom.getRoomName());
        Assert.assertEquals(room.getPlayers(), resultRoom.getPlayers());
        Assert.assertEquals(room.getRoomId(), resultRoom.getRoomId());
    }

    @Test
    public void getRoomTest() {
        final String roomId = UUID.randomUUID().toString();
        final String roomName = "Test room";
        final String ownerId = UUID.randomUUID().toString();

        final RoomWithOptions room = new RoomWithOptions(roomId, roomName, ownerId);
        final List<Player> mockPlayers = mock(List.class);

        when(roomRepository.getById(roomId)).thenReturn(room);
        when(roomRepository.getPlayers(roomId)).thenReturn(mockPlayers);

        final GetRoomResponse resultRoomResponse = roomService.getRoom(roomId);

        verify(roomRepository, times(1)).getById(roomId);
        verify(roomRepository, times(1)).getPlayers(roomId);

        Assert.assertEquals(roomId, resultRoomResponse.getRoomId());
        Assert.assertEquals(roomName, resultRoomResponse.getRoomName());
        Assert.assertEquals(mockPlayers, resultRoomResponse.getPlayers());
    }

    @Test
    public void joinRoom() throws SQLException {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        roomService.joinRoom(roomId, playerId);

        verify(roomRepository, times(1)).update(roomId, playerId);
    }
}
