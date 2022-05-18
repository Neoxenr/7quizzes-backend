package it.sevenbits.sevenquizzes.core.repository.room;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PosgresRoomRepositoryTest {
    private RoomRepository roomRepository;

    private JdbcOperations mockJdbcOperations;

    @Before
    public void setUp() {
        mockJdbcOperations = mock(JdbcTemplate.class);

        roomRepository = new PostgresRoomRepository(mockJdbcOperations);
    }

    @Test
    public void getAllTest() {
        final String sqlQuery = "SELECT * FROM room";

        final List<RoomWithOptions> mockRooms = mock(List.class);

        when(mockJdbcOperations.query(eq(sqlQuery), any(RowMapper.class))).thenReturn(mockRooms);

        final List<RoomWithOptions> resultRooms = roomRepository.getAll();

        verify(mockJdbcOperations, times(1)).query(eq(sqlQuery), any(RowMapper.class));

        Assert.assertEquals(mockRooms, resultRooms);
    }

    @Test
    public void getByIdTest() {
        final String roomId = UUID.randomUUID().toString();
        final String query = "SELECT * FROM room WHERE id = ?";

        final RoomWithOptions mockRoom = mock(RoomWithOptions.class);

        when(mockJdbcOperations.queryForObject(eq(query), any(RowMapper.class), eq(roomId))).thenReturn(mockRoom);

        final RoomWithOptions room = roomRepository.getById(roomId);

        verify(mockJdbcOperations, times(1)).queryForObject(eq(query), any(RowMapper.class), eq(roomId));

        Assert.assertEquals(mockRoom, room);
    }

    @Test
    public void createTest() throws SQLException {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();
        final String roomName = "Test room";

        final String firstQuery = "INSERT INTO room (id, name, owner_id) VALUES (?, ?, ?)";
        final String secondQuery = "INSERT INTO rooms_users (room_id, user_id) VALUES (?, ?)";

        final CreateRoomResponse mockCreateRoomResponse = mock(CreateRoomResponse.class);

        when(mockJdbcOperations.update(firstQuery, roomId, roomName, playerId)).thenReturn(1);
        when(mockJdbcOperations.update(secondQuery, roomId, playerId)).thenReturn(1);
        when(mockCreateRoomResponse.getRoomId()).thenReturn(roomId);
        when(mockCreateRoomResponse.getRoomName()).thenReturn(roomName);

        final CreateRoomResponse createRoomResponse = roomRepository.create(roomId, playerId, roomName);

        verify(mockJdbcOperations, times(1)).update(firstQuery, roomId, roomName, playerId);
        verify(mockJdbcOperations, times(1)).update(secondQuery, roomId, playerId);

        Assert.assertEquals(mockCreateRoomResponse.getRoomId(), createRoomResponse.getRoomId());
        Assert.assertEquals(mockCreateRoomResponse.getRoomName(), createRoomResponse.getRoomName());

        verify(mockCreateRoomResponse, times(1)).getRoomId();
        verify(mockCreateRoomResponse, times(1)).getRoomName();
    }

    @Test
    public void updateTest() throws SQLException {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        final String firstSqlQuery = "SELECT COUNT(user_id) AS count from rooms_users WHERE room_id = ?";
        final String secondSqlQuery = "INSERT INTO rooms_users (room_id, user_id) VALUES (?, ?)";

        when(mockJdbcOperations.queryForObject(eq(firstSqlQuery), any(RowMapper.class), eq(roomId))).thenReturn(0);
        when(mockJdbcOperations.update(secondSqlQuery, roomId, playerId)).thenReturn(1);

        roomRepository.update(roomId, playerId);

        verify(mockJdbcOperations, times(1)).queryForObject(eq(firstSqlQuery), any(RowMapper.class), eq(roomId));
        verify(mockJdbcOperations, times(1)).update(secondSqlQuery, roomId, playerId);
    }

    @Test
    public void getPlayersTest() {
        final String roomId = UUID.randomUUID().toString();
        final String sqlQuery = "SELECT user_id FROM rooms_users WHERE room_id = ?";

        final List<Player> mockRoomPlayers = mock(List.class);

        when(mockJdbcOperations.query(eq(sqlQuery), any(RowMapper.class), eq(roomId))).thenReturn(mockRoomPlayers);

        final List<Player> roomPlayers = roomRepository.getPlayers(roomId);

        verify(mockJdbcOperations, times(1)).query(eq(sqlQuery), any(RowMapper.class), eq(roomId));

        Assert.assertEquals(mockRoomPlayers, roomPlayers);
    }

    @Test
    public void containsTest() {
        final String roomId = UUID.randomUUID().toString();
        final String sqlQuery = "SELECT COUNT(id) as count FROM room WHERE id = ?";

        when(mockJdbcOperations.queryForObject(eq(sqlQuery), any(RowMapper.class), eq(roomId))).thenReturn(1);

        final boolean isContains = roomRepository.contains(roomId);

        verify(mockJdbcOperations, times(1)).queryForObject(eq(sqlQuery), any(RowMapper.class), eq(roomId));

        Assert.assertTrue(isContains);
    }
}
