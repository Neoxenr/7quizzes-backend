package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class RoomRepositoryStaticTest {
    private RoomRepository roomRepository;
    private Map<String, RoomWithOptions> roomsMock;
    private Map<String, List<Player>> roomsPlayersMock;

    @Before
    public void setUp() {
        roomsMock = mock(Map.class);
        roomsPlayersMock = mock(Map.class);
        roomRepository = new RoomRepositoryStatic(roomsMock, roomsPlayersMock);
    }

    @Test
    public void getRoomsTest() {
        final List<RoomWithOptions> rooms = new ArrayList<>();

        final RoomWithOptions firstRoom = new RoomWithOptions(UUID.randomUUID().toString(), "room 1");
        final RoomWithOptions secondRoom = new RoomWithOptions(UUID.randomUUID().toString(), "room 2");

        rooms.add(firstRoom);
        rooms.add(secondRoom);

        when(roomsMock.values()).thenReturn(rooms);

        final List<RoomWithOptions> resultRooms = roomRepository.getRooms();

        verify(roomsMock, times(1)).values();

        Assert.assertEquals(rooms, resultRooms);
    }

    @Test
    public void addRoomTest() {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();
        final String roomName = "room 1";

        final List<Player> players = new ArrayList<>();
        players.add(new Player(playerId));

        final CreateRoomResponse createRoomResponse = new CreateRoomResponse(roomId, roomName, players);

        final CreateRoomResponse createRoomResponseResult = roomRepository.addRoom(roomId, playerId, roomName);

        Assert.assertEquals(createRoomResponse.getRoomId(), createRoomResponseResult.getRoomId());
        Assert.assertEquals(createRoomResponse.getRoomName(), createRoomResponseResult.getRoomName());
        Assert.assertEquals(createRoomResponse.getPlayers(), createRoomResponseResult.getPlayers());
    }

    @Test
    public void getRoomByIdTest() {
        final String roomId = UUID.randomUUID().toString();
        final RoomWithOptions roomMock = mock(RoomWithOptions.class);

        when(roomsMock.get(roomId)).thenReturn(roomMock);

        final RoomWithOptions roomResult = roomRepository.getRoomById(roomId);

        verify(roomsMock, times(1)).get(roomId);

        Assert.assertEquals(roomMock, roomResult);
    }

    @Test
    public void addPlayerTest() {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        List<Player> players = new ArrayList<>();

        when(roomsPlayersMock.get(roomId)).thenReturn(players);

        roomRepository.addPlayer(roomId, playerId);

        verify(roomsPlayersMock, times(1)).get(roomId);

        Assert.assertEquals(players.size(), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPlayerExceptionTest() {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        Player player = new Player(playerId);

        List<Player> players = new ArrayList<>();
        players.add(player);

        when(roomsPlayersMock.get(roomId)).thenReturn(players);

        roomRepository.addPlayer(roomId, playerId);

        verify(roomsPlayersMock, times(1)).get(roomId);
    }

    @Test
    public void getPlayersTest() {
        final String roomId = UUID.randomUUID().toString();

        List<Player> players = mock(List.class);

        when(roomsPlayersMock.get(roomId)).thenReturn(players);

        List<Player> playersResult = roomRepository.getPlayers(roomId);

        verify(roomsPlayersMock, times(1)).get(roomId);

        Assert.assertEquals(players, playersResult);
    }
}
