package com.example.innowise.service;

import org.jeasy.rules.api.Rules;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileReader;

public class ReaderRuleYml {

    @Autowired
    private MVELRuleFactory ruleFactory;

    private final String rulePath = "src/main/resources/rules/"; //todo

    public Rules getRulesFromYml(String ruleFile) throws Exception {
        return ruleFactory.createRules(new FileReader(rulePath + ruleFile));
    }

}
