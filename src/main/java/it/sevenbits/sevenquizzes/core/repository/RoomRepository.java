package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomsResponse;

public interface RoomRepository {
    GetRoomsResponse getRooms();
    CreateRoomResponse addRoom(String roomId, String playerId, String roomName);
    GetRoomResponse getRoom(String id);
    void addPlayer(String id, String playerId);
}
