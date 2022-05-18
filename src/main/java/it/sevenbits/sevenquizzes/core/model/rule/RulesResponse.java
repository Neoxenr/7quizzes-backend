package it.sevenbits.sevenquizzes.core.model.rule;

import java.util.List;

public class RulesResponse {
    private final List<Rule> rules;

    /**
     * RulesResponse constructor
     *
     * @param rules - game rules
     */
    public RulesResponse(final List<Rule> rules) {
        this.rules = rules;
    }

    public List<Rule> getRules() {
        return rules;
    }
}
