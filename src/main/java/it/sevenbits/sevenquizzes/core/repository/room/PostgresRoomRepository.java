package it.sevenbits.sevenquizzes.core.repository.room;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostgresRoomRepository implements RoomRepository {
    private final JdbcOperations jdbcOperations;

    public PostgresRoomRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<RoomWithOptions> getAll() {
        return jdbcOperations.query(
                "SELECT id, name FROM room",
                (resultSet, i) -> {
                    final String rowRoomId = resultSet.getString("id");
                    final String rowRoomName = resultSet.getString("name");
                    return new RoomWithOptions(rowRoomId, rowRoomName);
                }
        );
    }

    @Override
    public RoomWithOptions getById(final String roomId) {
        return jdbcOperations.queryForObject(
                "SELECT id, \"name\" FROM room WHERE id = ?",
                (resultSet, i) -> {
                    final String rowRoomId = resultSet.getString("id");
                    final String rowRoomName = resultSet.getString("name");
                    return new RoomWithOptions(rowRoomId, rowRoomName);
                },
                roomId);
    }

    @Override
    public CreateRoomResponse create(final String roomId, final String playerId, final String roomName) throws SQLException {
        final List<Player> players = new ArrayList<>();

        players.add(new Player(playerId));

        jdbcOperations.update(
                "INSERT INTO room (id, name, users_id) VALUES (?, ?, ?)",
                roomId, roomName, Objects.requireNonNull(((JdbcTemplate) jdbcOperations).getDataSource())
                        .getConnection().createArrayOf("text", players.toArray())
        );

        return new CreateRoomResponse(roomId, roomName, players);
    }

    @Override
    public void update(final String roomId, final String playerId) throws SQLException {
        final List<Player> roomPlayers = getPlayers(roomId);
        System.out.println(roomPlayers);
        final Player newPlayer = new Player(playerId);

        if (roomPlayers.contains(newPlayer)) {
            throw new IllegalArgumentException("Player with current id already exists");
        }

        roomPlayers.add(newPlayer);

        jdbcOperations.update(
                "UPDATE room SET users_id = ? WHERE id = ?",
                Objects.requireNonNull(((JdbcTemplate) jdbcOperations).getDataSource())
                        .getConnection().createArrayOf("text", roomPlayers.toArray()), roomId
        );
    }

    @Override
    public List<Player> getPlayers(final String roomId) {
        return jdbcOperations.queryForObject(
                "SELECT users_id FROM room WHERE id = ?",
                (resultSet, i) -> {
                    final Array rowArray = resultSet.getArray("users_id");
                    final String[] playersId = (String[]) rowArray.getArray();

                    final List<Player> players = new ArrayList<>();

                    for (final String playerId : playersId) {
                        players.add(new Player(playerId));
                    }

                    return players;
                },
                roomId);
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
