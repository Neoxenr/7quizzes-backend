package it.sevenbits.sevenquizzes.config;

import it.sevenbits.sevenquizzes.core.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    /**
     * Returns GameRepository
     *
     * @return GameRepository - repository for storing game data
     */
    @Bean
    public GameRepository gameRepository() {
        return new GameRepositoryStatic();
    }

    /**
     * Returns QuestionRepository
     *
     * @return QuestionRepository - repository for storing question data
     */
    @Bean
    public QuestionRepository questionRepository() {
        return new QuestionRepositoryStatic();
    }

    @Bean
    public RoomRepository roomRepository() {return new RoomRepositoryStatic();}
}
