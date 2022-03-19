package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.GetRoomsResponse;
import it.sevenbits.sevenquizzes.core.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(final RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public GetRoomsResponse getRooms() {
        return roomRepository.getRooms();
    }

    public CreateRoomResponse createRoom(final String playerId, final String roomName) {
        final String newRoomId = "b8a71c88-7ae0-4a54-9697-9ceb5a1f8fc" + new Random().nextInt(10);

        return roomRepository.addRoom(newRoomId, playerId, roomName);
    }

    public GetRoomResponse getRoom(final String id) {
        return roomRepository.getRoom(id);
    }

    public void joinRoom(final String id, final String playerId) {
        roomRepository.addPlayer(id, playerId);
    }
}
