package it.sevenbits.sevenquizzes.core.model.room;

import java.util.List;

public class GetRoomsResponse {
    private final List<RoomWithOptions> rooms;

    /**
     * GetRoomsResponse constructor
     *
     * @param rooms - rooms
     */
    public GetRoomsResponse(final List<RoomWithOptions> rooms) {
        this.rooms = rooms;
    }

    /**
     * Return rooms
     *
     * @return List<RoomWithOptions> - rooms
     */
    public List<RoomWithOptions> getRooms() {
        return rooms;
    }
}
