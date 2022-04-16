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
        final String sqlQuery = "SELECT id, name FROM room";

        final List<RoomWithOptions> mockRooms = mock(List.class);

        when(mockJdbcOperations.query(eq(sqlQuery), any(RowMapper.class))).thenReturn(mockRooms);

        final List<RoomWithOptions> resultRooms = roomRepository.getAll();

        verify(mockJdbcOperations, times(1)).query(eq(sqlQuery), any(RowMapper.class));

        Assert.assertEquals(mockRooms, resultRooms);
    }

    @Test
    public void getByIdTest() {
        final String roomId = UUID.randomUUID().toString();
        final String sqlQuery = "SELECT id, \"name\" FROM room WHERE id = ?";

        final RoomWithOptions mockRoom = mock(RoomWithOptions.class);

        when(mockJdbcOperations.queryForObject(eq(sqlQuery), any(RowMapper.class), eq(roomId))).thenReturn(mockRoom);

        final RoomWithOptions room = roomRepository.getById(roomId);

        verify(mockJdbcOperations, times(1)).queryForObject(eq(sqlQuery), any(RowMapper.class), eq(roomId));

        Assert.assertEquals(mockRoom, room);
    }

    @Test
    public void createTest() throws SQLException {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();
        final String roomName = "Test room";

        final String sqlQuery = "INSERT INTO room (id, name, users_id) VALUES (?, ?, ?)";

        final DataSource mockDataSource = mock(DataSource.class);
        final Connection mockConnection = mock(Connection.class);
        final Array mockArray = mock(Array.class);

        when(mockJdbcOperations.update(eq(sqlQuery), eq(roomId), eq(roomName), any(Array.class))).thenReturn(1);
        when(((JdbcTemplate) mockJdbcOperations).getDataSource()).thenReturn(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createArrayOf(eq("text"), any(Object[].class))).thenReturn(mockArray);

        final CreateRoomResponse room = roomRepository.create(roomId, playerId, roomName);

        verify(mockJdbcOperations, times(1)).update(eq(sqlQuery), eq(roomId), eq(roomName), any(Array.class));
        verify((JdbcTemplate) mockJdbcOperations, times(1)).getDataSource();
        verify(mockDataSource, times(1)).getConnection();
        verify(mockConnection, times(1)).createArrayOf(eq("text"), any(Object[].class));

        Assert.assertEquals(room.getRoomId(), roomId);
        Assert.assertEquals(room.getRoomName(), roomName);
        Assert.assertEquals(room.getPlayers().size(), 1);
    }

    @Test
    public void updateTest() throws SQLException {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        final String firstSqlQuery = "SELECT users_id FROM room WHERE id = ?";
        final String secondSqlQuery = "UPDATE room SET users_id = ? WHERE id = ?";

        final List<Player> mockRoomPlayers = mock(List.class);
        final DataSource mockDataSource = mock(DataSource.class);
        final Connection mockConnection = mock(Connection.class);
        final Array mockArray = mock(Array.class);

        final Object[] array = new Object[]{};

        when(mockJdbcOperations.queryForObject(eq(firstSqlQuery), any(RowMapper.class), eq(roomId))).thenReturn(mockRoomPlayers);
        when(mockRoomPlayers.contains(new Player(playerId))).thenReturn(false);
        when(mockRoomPlayers.add(new Player(playerId))).thenReturn(true);
        when(mockJdbcOperations.update(eq(secondSqlQuery), any(Array.class), eq(roomId))).thenReturn(1);
        when(((JdbcTemplate) mockJdbcOperations).getDataSource()).thenReturn(mockDataSource);
        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createArrayOf(eq("text"), any(Object[].class))).thenReturn(mockArray);
        when(mockRoomPlayers.toArray()).thenReturn(array);

        roomRepository.update(roomId, playerId);

        verify(mockJdbcOperations, times(1)).queryForObject(eq(firstSqlQuery), any(RowMapper.class), eq(roomId));
        verify(mockRoomPlayers, times(1)).contains(new Player(playerId));
        verify(mockRoomPlayers, times(1)).add(new Player(playerId));
        verify(mockJdbcOperations, times(1)).update(eq(secondSqlQuery), any(Array.class), eq(roomId));
        verify((JdbcTemplate) mockJdbcOperations, times(1)).getDataSource();
        verify(mockDataSource, times(1)).getConnection();
        verify(mockConnection, times(1)).createArrayOf(eq("text"), any(Object[].class));
        verify(mockRoomPlayers, times(1)).toArray();
    }

    @Test
    public void getPlayersTest() {
        final String roomId = UUID.randomUUID().toString();
        final String sqlQuery = "SELECT users_id FROM room WHERE id = ?";

        final List<Player> mockRoomPlayers = mock(List.class);

        when(mockJdbcOperations.queryForObject(eq(sqlQuery), any(RowMapper.class), eq(roomId))).thenReturn(mockRoomPlayers);

        final List<Player> roomPlayers = roomRepository.getPlayers(roomId);

        verify(mockJdbcOperations, times(1)).queryForObject(eq(sqlQuery), any(RowMapper.class), eq(roomId));

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
