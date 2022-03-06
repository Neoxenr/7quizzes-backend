package it.sevenbits.sevenquizzes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
class SevenQuizzesApplication {
    /**
     * Input point to the web application
     *
     * @param args - system args
     */
    public static void main(final String[] args) {
        SpringApplication.run(SevenQuizzesApplication.class, args);
    }
}