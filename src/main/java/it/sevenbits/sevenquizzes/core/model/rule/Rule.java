package it.sevenbits.sevenquizzes.core.model.rule;

public class Rule {
    private final String ruleId;

    private final String ruleText;

    /**
     * Rule constructor
     *
     * @param ruleId - rule id
     * @param ruleText - rule text
     */
    public Rule(final String ruleId, final String ruleText) {
        this.ruleId = ruleId;
        this.ruleText = ruleText;
    }

    public String getRuleId() {
        return ruleId;
    }

    public String getRuleText() {
        return ruleText;
    }
}
