package it.sevenbits.sevenquizzes.core.repository.rule;

import it.sevenbits.sevenquizzes.core.model.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

public class PostgresRuleRepository implements RuleRepository {
    private final Logger logger = LoggerFactory.getLogger("it.sevenbits.sevenquizzes.core.repository.rule.logger");

    private final JdbcOperations jdbcOperations;

    /**
     * PostgresRuleRepository constructor
     *
     * @param jdbcOperations - jdbc operations
     */
    public PostgresRuleRepository(final JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Rule> getRules() {
        logger.info("Getting all rules");
        return jdbcOperations.query(
                "SELECT * FROM rule",
                (resultSet, i) -> {
                    final String ruleId = resultSet.getString("id");
                    final String ruleText = resultSet.getString("text");
                    return new Rule(ruleId, ruleText);
                }
        );
    }
}
