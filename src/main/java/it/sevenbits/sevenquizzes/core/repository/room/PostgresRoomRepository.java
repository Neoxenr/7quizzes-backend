package it.sevenbits.sevenquizzes.core.repository.room;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostgresRoomRepository implements RoomRepository {
    private final JdbcOperations jdbcOperations;

    /**
     * Constructor
     *
     * @param jdbcOperations - jdbc operations
     */
    public PostgresRoomRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<RoomWithOptions> getAll() {
        return jdbcOperations.query(
                "SELECT * FROM room",
                (resultSet, i) -> {
                    final String roomId = resultSet.getString("id");
                    final String roomName = resultSet.getString("name");
                    final String ownerId = resultSet.getString("owner_id");
                    return new RoomWithOptions(roomId, roomName, ownerId);
                }
        );
    }

    @Override
    public RoomWithOptions getById(final String roomId) {
        try {
            final RoomWithOptions room = jdbcOperations.queryForObject(
                    "SELECT * FROM room WHERE id = ?",
                    (resultSet, i) -> {
                        final String rowRoomId = resultSet.getString("id");
                        final String rowRoomName = resultSet.getString("name");
                        final String rowOwnerId = resultSet.getString("owner_id");
                        return new RoomWithOptions(rowRoomId, rowRoomName, rowOwnerId);
                    },
                    roomId);
            return room;
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public CreateRoomResponse create(
            final String roomId,
            final String playerId,
            final String roomName) throws SQLException {
        jdbcOperations.update(
                "INSERT INTO room (id, name, owner_id) VALUES (?, ?, ?)",
                roomId, roomName, playerId
        );

        jdbcOperations.update(
                "INSERT INTO rooms_users (room_id, user_id) VALUES (?, ?)",
                roomId, playerId
        );

        List<Player> players = new ArrayList<>();
        players.add(new Player(playerId));

        return new CreateRoomResponse(roomId, roomName, playerId, players);
    }

    @Override
    public void update(final String roomId, final String playerId) throws SQLException {
        final boolean isUserExist = jdbcOperations.queryForObject(
                "SELECT COUNT(user_id) AS count from rooms_users WHERE room_id = ?",
                (resultSet, k) -> resultSet.getInt("count"),
                roomId
        ) > 0;

        if (isUserExist) {
            throw new IllegalArgumentException("Player with current id already exists");
        }

        jdbcOperations.update(
                "INSERT INTO rooms_users (room_id, user_id) VALUES (?, ?)",
                roomId, playerId
        );
    }

    @Override
    public List<Player> getPlayers(final String roomId) {
        return jdbcOperations.query(
                "SELECT user_id FROM rooms_users WHERE room_id = ?",
                (resultSet, k) -> new Player(resultSet.getString("user_id")),
                roomId
        );
    }

    @Override
    public boolean contains(final String roomId) {
        return jdbcOperations.queryForObject(
                "SELECT COUNT(id) as count FROM room WHERE id = ?",
                (resultSet, i) -> resultSet.getInt("count"),
                roomId
        ) > 0;
    }
}
