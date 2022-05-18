package it.sevenbits.sevenquizzes.core.model.user;

import java.util.List;

public class GetUsersResponse {
    private final List<GetUserResponse> users;

    /**
     * GetUsersResponse constructor
     *
     * @param users - users
     */
    public GetUsersResponse(final List<GetUserResponse> users) {
        this.users = users;
    }

    public List<GetUserResponse> getUsers() {
        return users;
    }
}
