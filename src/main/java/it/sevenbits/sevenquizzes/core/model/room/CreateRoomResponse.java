package it.sevenbits.sevenquizzes.core.model.room;

import java.util.List;

public class CreateRoomResponse extends RoomWithOptions {
    private final List<String> players;

    public CreateRoomResponse(final String roomId, final String roomName, final List<String> players) {
        super(roomId, roomName);
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
    }
}
