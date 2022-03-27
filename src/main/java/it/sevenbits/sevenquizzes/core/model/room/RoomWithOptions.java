package it.sevenbits.sevenquizzes.core.model.room;

public class RoomWithOptions {
    private final String roomId;
    private final String roomName;

    /**
     * RoomWithOptions constructor
     *
     * @param roomId - room id
     * @param roomName - room name
     */
    public RoomWithOptions(final String roomId, final String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
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
}
