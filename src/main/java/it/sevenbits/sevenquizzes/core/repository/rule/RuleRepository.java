package it.sevenbits.sevenquizzes.core.repository.rule;

import it.sevenbits.sevenquizzes.core.model.rule.Rule;

import java.util.List;

public interface RuleRepository {
    /**
     * Get all rules for game
     *
     * @return List<Rule> - list of rules
     */
    List<Rule> getRules();
}
