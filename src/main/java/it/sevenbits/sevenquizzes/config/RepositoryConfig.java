package it.sevenbits.sevenquizzes.config;

import it.sevenbits.sevenquizzes.core.repository.game.GameRepository;
import it.sevenbits.sevenquizzes.core.repository.game.GameRepositoryStatic;
import it.sevenbits.sevenquizzes.core.repository.question.PostgresQuestionRepository;
import it.sevenbits.sevenquizzes.core.repository.question.QuestionRepository;
import it.sevenbits.sevenquizzes.core.repository.room.PostgresRoomRepository;
import it.sevenbits.sevenquizzes.core.repository.room.RoomRepository;
import it.sevenbits.sevenquizzes.core.repository.rule.PostgresRuleRepository;
import it.sevenbits.sevenquizzes.core.repository.rule.RuleRepository;
import it.sevenbits.sevenquizzes.core.repository.user.PostgresUserRepository;
import it.sevenbits.sevenquizzes.core.repository.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.HashMap;

@Configuration
public class RepositoryConfig {
    /**
     * Returns GameRepository
     *
     * @return GameRepository - repository for storing game data
     */
    @Bean
    public GameRepository gameRepository() {
        return new GameRepositoryStatic(new HashMap<>());
    }

    /**
     * Returns QuestionRepository
     *
     * @return QuestionRepository - repository for storing question data
     */
    @Bean
    public QuestionRepository questionRepository(final JdbcOperations jdbcOperations) {
        return new PostgresQuestionRepository(jdbcOperations);
    }

    /**
     * Return RoomRepository
     *
     * @return RoomRepository - repository for storing room data
     */
    @Bean
    public RoomRepository roomRepository(final JdbcOperations jdbcOperations) {
        return new PostgresRoomRepository(jdbcOperations);
    }

    /**
     * Return UserRepository
     *
     * @param jdbcOperations - jdbc operations
     * @return UserRepository - repository for storing user data
     */
    @Bean
    public UserRepository userRepository(final JdbcOperations jdbcOperations) {
        return new PostgresUserRepository(jdbcOperations);
    }

    /**
     * Return RuleRepository
     *
     * @param jdbcOperations - jdbc operations
     * @return RuleRepository - repository for storing rule data
     */
    @Bean
    public RuleRepository ruleRepository(final JdbcOperations jdbcOperations) {
        return new PostgresRuleRepository(jdbcOperations);
    }
}
