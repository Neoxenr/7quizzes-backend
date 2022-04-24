package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomsResponse;
import it.sevenbits.sevenquizzes.core.repository.room.RoomRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.UUID;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    /**
     * RoomService constructor
     *
     * @param roomRepository - room repository
     */
    public RoomService(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Return all rooms from repository
     *
     * @return GetRoomsResponse - all rooms from repository
     */
    public GetRoomsResponse getRooms() {
        return new GetRoomsResponse(roomRepository.getAll());
    }

    /**
     * Create new room in repository
     *
     * @param playerId - player id
     * @param roomName - room name
     * @return CreateRoomResponse - new room model
     */
    public CreateRoomResponse createRoom(final String playerId, final String roomName) throws SQLException {
        final String roomId = UUID.randomUUID().toString();

        return roomRepository.create(roomId, playerId, roomName);
    }

    /**
     * Returns room by id
     *
     * @param roomId - room id
     * @return GetRoomResponse - room model
     */
    public GetRoomResponse getRoom(final String roomId) {
        return new GetRoomResponse(roomRepository.getById(roomId), roomRepository.getPlayers(roomId));
    }

    /**
     * Joins player to room
     *
     * @param roomId - room id
     * @param playerId - player id
     */
    public void joinRoom(final String roomId, final String playerId) throws SQLException {
        roomRepository.update(roomId, playerId);
    }
}
