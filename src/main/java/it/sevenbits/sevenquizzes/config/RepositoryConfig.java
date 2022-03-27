package it.sevenbits.sevenquizzes.config;

import it.sevenbits.sevenquizzes.core.repository.GameRepository;
import it.sevenbits.sevenquizzes.core.repository.GameRepositoryStatic;
import it.sevenbits.sevenquizzes.core.repository.QuestionRepository;
import it.sevenbits.sevenquizzes.core.repository.QuestionRepositoryStatic;
import it.sevenbits.sevenquizzes.core.repository.RoomRepository;
import it.sevenbits.sevenquizzes.core.repository.RoomRepositoryStatic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public QuestionRepository questionRepository() {
        return new QuestionRepositoryStatic(new HashMap<>(), new HashMap<>());
    }

    /**
     * Return RoomRepository
     *
     * @return RoomRepository - repository for storing room data
     */
    @Bean
    public RoomRepository roomRepository() {
        return new RoomRepositoryStatic(new HashMap<>(), new HashMap<>());
    }
}
