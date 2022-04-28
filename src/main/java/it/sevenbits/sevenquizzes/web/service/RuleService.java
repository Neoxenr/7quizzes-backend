package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.rule.RulesResponse;
import it.sevenbits.sevenquizzes.core.repository.rule.RuleRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleService {
    private final RuleRepository ruleRepository;

    /**
     * RuleService constructor
     *
     * @param ruleRepository - rule repository
     */
    public RuleService(final RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public RulesResponse getRules() {
        return new RulesResponse(ruleRepository.getRules());
    }
}
