package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;

import java.util.List;

public interface RoomRepository {
    /**
     * Returns all rooms in repository
     *
     * @return List<RoomWithOptions> - all rooms in repository
     */
    List<RoomWithOptions> getRooms();

    /**
     * Creates new room
     *
     * @param roomId - room id
     * @param playerId - player id
     * @param roomName - room name
     * @return CreateRoomResponse - model for room
     */
    CreateRoomResponse addRoom(String roomId, String playerId, String roomName);

    /**
     * Return RoomWithOptions model by id
     *
     * @param roomId - room id
     * @return RoomWithOptions - model for room
     */
    RoomWithOptions getRoomById(String roomId);

    /**
     * Adds player to the room
     *
     * @param roomId - room id
     * @param playerId - player id
     */
    void addPlayer(String roomId, String playerId);

    /**
     * Returns players in the room
     *
     * @param roomId - room id
     * @return List<Player> - players in the room
     */
    List<Player> getPlayers(String roomId);

    /**
     * Checks that key with room id exists
     *
     * @param roomId - room id
     * @return boolean - true if room with current id exists
     */
    boolean contains(String roomId);
}
