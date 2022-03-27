package it.sevenbits.sevenquizzes.core.model.room;

import it.sevenbits.sevenquizzes.core.model.player.Player;

import java.util.List;

public class GetRoomResponse {
    private final String roomId;
    private final String roomName;

    private final List<Player> players;

    /**
     * GetRoomResponse constuctor
     *
     * @param room - room
     * @param players - players
     */
    public GetRoomResponse(final RoomWithOptions room, final List<Player> players) {
        this.roomId = room.getRoomId();
        this.roomName = room.getRoomName();
        this.players = players;
    }

    /**
     * Returns room id
     *
     * @return String - room id
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * Returns room name
     *
     * @return String - room name
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Returns players in the room
     *
     * @return List<Players> - players in the room
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Adds player to the room
     *
     * @param playerId - player id
     */
    public void addPlayer(final String playerId) {
        players.add(new Player(playerId));
    }
}
