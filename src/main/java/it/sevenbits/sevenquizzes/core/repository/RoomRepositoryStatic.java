package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomsResponse;

import java.util.*;

public class RoomRepositoryStatic implements RoomRepository {
    private final Map<String, GetRoomResponse> rooms;

    public RoomRepositoryStatic() {
        this.rooms = new HashMap<>();
    }

    @Override
    public GetRoomsResponse getRooms() {
        return new GetRoomsResponse(new ArrayList<>(rooms.values()));
    }

    @Override
    public CreateRoomResponse addRoom(final String roomId, final String playerId, final String roomName) {
        List<String> players = new ArrayList<>();
        players.add(playerId);

        final GetRoomResponse newRoom = new GetRoomResponse(roomId, roomName, players);
        rooms.put(roomId, newRoom);

        return new CreateRoomResponse(roomId, roomName, players);
    }

    @Override
    public GetRoomResponse getRoom(final String id) {
        return rooms.get(id);
    }

    @Override
    public void addPlayer(final String id, final String playerId) {
        final GetRoomResponse room = rooms.get(id);

        room.addPlayer(playerId);
    }
}
