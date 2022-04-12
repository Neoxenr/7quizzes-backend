package it.sevenbits.sevenquizzes.core.model.player;

import java.util.Objects;

public class Player {
    private final String playerId;

    /**
     * Player constructor
     *
     * @param playerId - player id
     */
    public Player(final String playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns player id
     *
     * @return String - player id
     */
    public String getPlayerId() {
        return playerId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player) o;

        return Objects.equals(playerId, player.playerId);
    }

    @Override
    public int hashCode() {
        return playerId != null ? playerId.hashCode() : 0;
    }
}
