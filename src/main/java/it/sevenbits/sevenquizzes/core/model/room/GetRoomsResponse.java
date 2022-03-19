package it.sevenbits.sevenquizzes.core.model.room;

import java.util.List;

public class GetRoomsResponse {
    private List<RoomWithOptions> rooms;

    public GetRoomsResponse(final List<RoomWithOptions> rooms) {
        this.rooms = rooms;
    }

    public List<RoomWithOptions> getRooms() {
        return rooms;
    }
}
