package it.sevenbits.sevenquizzes.core.repository.room;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;

import java.sql.SQLException;
import java.util.List;

public interface RoomRepository {
    /**
     * Return all rooms in repository
     *
     * @return List<RoomWithOptions> - all rooms in repository
     */
    List<RoomWithOptions> getAll();

    /**
     * Return RoomWithOptions model by id
     *
     * @param roomId - room id
     * @return RoomWithOptions - model for room
     */
    RoomWithOptions getById(String roomId);

    /**
     * Create new room
     *
     * @param roomId - room id
     * @param playerId - player id
     * @param roomName - room name
     * @return CreateRoomResponse - model for room
     */
    CreateRoomResponse create(String roomId, String playerId, String roomName) throws SQLException;

    /**
     * Add player to the room
     *
     * @param roomId - room id
     * @param playerId - player id
     */
    void update(String roomId, String playerId) throws SQLException;

    /**
     * Return players in the room
     *
     * @param roomId - room id
     * @return List<Player> - players in the room
     */
    List<Player> getPlayers(String roomId);

    /**
     * Check that key with room id exists
     *
     * @param roomId - room id
     * @return boolean - true if room with current id exists
     */
    boolean contains(String roomId);
}
