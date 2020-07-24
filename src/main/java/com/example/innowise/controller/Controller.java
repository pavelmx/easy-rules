package com.example.innowise.controller;

import com.example.innowise.model.BaseModel;
import com.example.innowise.model.CompositeType;
import com.example.innowise.service.RulesService;
import org.jeasy.rules.api.Rules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class Controller<T extends BaseModel>{

    @Autowired
    private RulesService<T> rulesService;

    @PostMapping("/rules/models")
    public String assignRules(/*@RequestParam("") List<T> models, @RequestParam("") List<String> ruleNames*/) {
        List<String> ruleNames = new ArrayList<>();
        List<T> models = new ArrayList<>();
        return rulesService.assignRules(models, ruleNames);
    }

    @GetMapping
    public Collection<Rules> getRules() {
        return rulesService.getRules();
    }

    @PutMapping("/rules/composite/{compositeType}")
    public String createCompositeRule(@PathVariable CompositeType compositeType/*, @RequestParam("") List<String> ruleNames,@RequestParam("") String nameRule, String description, int priority*/) {
        List<String> ruleNames = new ArrayList<>();
        String nameRule = "testRuleGroup";
        String description = "testRuleGroup";
        int priority = 2;
        return rulesService.createCompositeRule(compositeType, ruleNames, nameRule, description, priority);
    }

}
