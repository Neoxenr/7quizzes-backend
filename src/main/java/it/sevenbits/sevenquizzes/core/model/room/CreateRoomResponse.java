package it.sevenbits.sevenquizzes.core.model.room;

import it.sevenbits.sevenquizzes.core.model.player.Player;

import java.util.List;

public class CreateRoomResponse extends RoomWithOptions {
    private final List<Player> players;

    /**
     *
     * @param roomId - room id
     * @param roomName - room name
     * @param players - players
     */
    public CreateRoomResponse(final String roomId, final String roomName, final List<Player> players) {
        super(roomId, roomName);
        this.players = players;
    }

    /**
     * Returns players in the room
     *
     * @return List<Player> - players in the room
     */
    public List<Player> getPlayers() {
        return players;
    }
}
