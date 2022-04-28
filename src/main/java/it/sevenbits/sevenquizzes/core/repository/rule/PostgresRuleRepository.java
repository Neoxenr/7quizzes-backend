package it.sevenbits.sevenquizzes.core.repository.rule;

import it.sevenbits.sevenquizzes.core.model.rule.Rule;
import org.springframework.jdbc.core.JdbcOperations;

import java.util.List;

public class PostgresRuleRepository implements RuleRepository {
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
