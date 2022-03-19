package it.sevenbits.sevenquizzes.core.model.room;

import java.util.List;

public class GetRoomResponse extends RoomWithOptions {
    private final List<String> players;

    public GetRoomResponse(final String roomId, final String roomName, final List<String> players) {
        super(roomId, roomName);
        this.players = players;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void addPlayer(final String playerId) {
        players.add(playerId);
    }
}
