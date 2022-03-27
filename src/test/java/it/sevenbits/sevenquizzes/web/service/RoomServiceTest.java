package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomsResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;
import it.sevenbits.sevenquizzes.core.repository.RoomRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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

        final RoomWithOptions firstRoom = new RoomWithOptions(UUID.randomUUID().toString(), "room 1");
        final RoomWithOptions secondRoom = new RoomWithOptions(UUID.randomUUID().toString(), "room 2");

        rooms.add(firstRoom);
        rooms.add(secondRoom);

        when(roomRepository.getRooms()).thenReturn(rooms);

        final GetRoomsResponse resultRooms = roomService.getRooms();

        verify(roomRepository, times(1)).getRooms();

        Assert.assertEquals(new GetRoomsResponse(rooms).getRooms(), resultRooms.getRooms());
    }

    @Test
    public void createRoomTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomName = "Test room";
        final String roomId = UUID.randomUUID().toString();

        final List<Player> players = new ArrayList<>();
        players.add(new Player(playerId));

        final CreateRoomResponse room = new CreateRoomResponse(roomId, roomName, players);

        when(roomRepository.addRoom(anyString(), eq(playerId), eq(roomName))).thenReturn(room);

        final CreateRoomResponse resultRoom = roomService.createRoom(playerId, roomName);

        verify(roomRepository, times(1)).addRoom(anyString(), eq(playerId), eq(roomName));

        Assert.assertEquals(room.getRoomName(), resultRoom.getRoomName());
        Assert.assertEquals(room.getPlayers(), resultRoom.getPlayers());
        Assert.assertEquals(room.getRoomId(), resultRoom.getRoomId());
    }

    @Test
    public void getRoomTest() {
        final String roomId = UUID.randomUUID().toString();
        final String roomName = "Test room";

        final RoomWithOptions room = new RoomWithOptions(roomId, roomName);
        final List<Player> mockPlayers = mock(List.class);

        when(roomRepository.getRoomById(roomId)).thenReturn(room);
        when(roomRepository.getPlayers(roomId)).thenReturn(mockPlayers);

        final GetRoomResponse resultRoomResponse = roomService.getRoom(roomId);

        verify(roomRepository, times(1)).getRoomById(roomId);
        verify(roomRepository, times(1)).getPlayers(roomId);

        Assert.assertEquals(roomId, resultRoomResponse.getRoomId());
        Assert.assertEquals(roomName, resultRoomResponse.getRoomName());
        Assert.assertEquals(mockPlayers, resultRoomResponse.getPlayers());
    }

    @Test
    public void joinRoom() {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        roomService.joinRoom(roomId, playerId);

        verify(roomRepository, times(1)).addPlayer(roomId, playerId);
    }
}
