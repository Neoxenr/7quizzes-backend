package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.user.GetUserResponse;
import it.sevenbits.sevenquizzes.core.model.user.GetUsersResponse;
import it.sevenbits.sevenquizzes.core.model.user.User;
import it.sevenbits.sevenquizzes.core.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * UserService constructor
     *
     * @param userRepository - user repository
     */
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Return all users in db
     *
     * @return GetUsersResponse - all users in db
     */
    public GetUsersResponse getUsers() {
        final List<User> users = userRepository.getAll();

        final List<GetUserResponse> getUserResponseList = new ArrayList<>();

        for (final User user : users) {
            getUserResponseList.add(new GetUserResponse(user.getUserId(), user.getUsername(), user.getRoles()));
        }

        return new GetUsersResponse(getUserResponseList);
    }

    /**
     * Return user in db
     *
     * @param userId - user id
     * @return GetUserResponse - user in db
     */
    public GetUserResponse getUser(final String userId) {
        final User user = userRepository.getById(userId);

        return new GetUserResponse(userId, user.getUsername(), user.getRoles());
    }
}
