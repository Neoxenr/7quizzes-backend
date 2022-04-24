package it.sevenbits.sevenquizzes.core.repository.room;

import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.room.CreateRoomResponse;
import it.sevenbits.sevenquizzes.core.model.room.RoomWithOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoomRepositoryStatic implements RoomRepository {
    private final Map<String, RoomWithOptions> rooms;
    private final Map<String, List<Player>> roomsPlayers;

    /**
     *
     * @param rooms - rooms
     * @param roomsPlayers - rooms players
     */
    public RoomRepositoryStatic(final Map<String, RoomWithOptions> rooms, final Map<String, List<Player>> roomsPlayers) {
        this.rooms = rooms;
        this.roomsPlayers = roomsPlayers;
    }

    /**
     * Returns all rooms in repository
     *
     * @return List<RoomWithOptions> - all rooms in repository
     */
    @Override
    public List<RoomWithOptions> getAll() {
        return new ArrayList<>(rooms.values());
    }

    /**
     * Creates new room
     *
     * @param roomId - room id
     * @param playerId - player id
     * @param roomName - room name
     * @return CreateRoomResponse - model for new room
     */
    @Override
    public CreateRoomResponse create(final String roomId, final String playerId, final String roomName) {
        final RoomWithOptions room = new RoomWithOptions(roomId, roomName);

        List<Player> players = new ArrayList<>();
        players.add(new Player(playerId));

        rooms.put(roomId, room);
        roomsPlayers.put(roomId, players);

        return new CreateRoomResponse(roomId, roomName, players);
    }

    /**
     * Return RoomWithOptions model by id
     *
     * @param roomId - room id
     * @return RoomWithOptions - model for room
     */
    @Override
    public RoomWithOptions getById(final String roomId) {
        return rooms.get(roomId);
    }

    /**
     * Add player to the room
     *
     * @param roomId - room id
     * @param playerId - player id
     */
    @Override
    public void update(final String roomId, final String playerId) {
        final List<Player> players = roomsPlayers.get(roomId);

        final Player player = new Player(playerId);

        if (players.contains(player)) {
            throw new IllegalArgumentException("Player with current id already exists");
        }

        players.add(player);
    }

    /**
     * Returns players in the room
     *
     * @param roomId - room id
     * @return List<Player> - players in the room
     */
    @Override
    public List<Player> getPlayers(final String roomId) {
        return roomsPlayers.get(roomId);
    }

    /**
     * Checks that key with room id exists
     *
     * @param roomId - room id
     * @return boolean - true if room id exists in map
     */
    @Override
    public boolean contains(final String roomId) {
        return rooms.containsKey(roomId);
    }
}
