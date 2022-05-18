package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.rule.RulesResponse;
import it.sevenbits.sevenquizzes.web.security.AuthRoleRequired;
import it.sevenbits.sevenquizzes.web.service.RuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rules")
public class RuleController {
    private final RuleService ruleService;

    /**
     * RuleController constructor
     *
     * @param ruleService - rule service
     */
    public RuleController(final RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @GetMapping
    @ResponseBody
    @AuthRoleRequired("USER")
    public ResponseEntity<RulesResponse> getRules() {
        return new ResponseEntity<>(ruleService.getRules(), HttpStatus.OK);
    }
}
