package com.example.innowise.service;

import com.example.innowise.model.BaseModel;
import com.example.innowise.model.CompositeType;
import com.example.innowise.model.Person;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.support.composite.ActivationRuleGroup;
import org.jeasy.rules.support.composite.CompositeRule;
import org.jeasy.rules.support.composite.ConditionalRuleGroup;
import org.jeasy.rules.support.composite.UnitRuleGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RulesServiceImpl<T extends BaseModel> implements RulesService<T> {

    @Autowired
    private ReaderRuleYml reader;

    @Autowired
    private RulesEngine rulesEngine;

    @Autowired
    private Facts facts;

    @Value("${rules.location}")
    private String rulesLocation;

    @PostConstruct
    private void initTestData() throws Exception {
        Set<String> files = listFiles(rulesLocation);
        for (String file : files) {
            createNewYmlRules(file);
        }

        Person pavel = new Person("pavel", "Pavel", 18);
        Person mark = new Person("mark", "Mark", 15);
        Person alex = new Person("alex", "Alex", 36);
        Person nikita = new Person("nikita", "Nikita", 10);
        Person mariya = new Person("mariya", "Mariya", 25);
        facts.put("pavel", pavel);
        facts.put("mark", mark);
        facts.put("alex", alex);
        facts.put("nikita", nikita);
        facts.put("mariya", mariya);
    }

    private Set<String> listFiles(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    private void printData() {
        facts.forEach(fact -> {
            System.out.println("\n" + fact.getValue().toString() + "\n");
        });
    }

    @Override
    public void fire(Rules rules, Facts facts) {
        printData();
        rulesEngine.fire(rules, facts);
        printData();
    }

    @Override
    public String assignRules(List<T> models, List<String> ruleNames) {
        Rules rules = new Rules();

        //todo
        //for example
        ruleNames.add("rule1");
        ruleNames.add("rule3");

        getRules().forEach(cRules -> {
            cRules.forEach(rule -> {
                if (ruleNames.contains(rule.getName())) {
                    rules.register(rule);
                }
            });
        });

        //todo
        //use it
//        fire(rules, getFactsFromModels(models));

        //todo
        //for example
        fire(rules, getFactsFromModels(List.of((T) facts.getFact("mark").getValue(), (T) facts.getFact("nikita").getValue()))); //todo

        String result = "executing...";
        for (Rule rule : rules) {
            result += "\n" + rule.getName();
        }

        return result;
    }


    @Override
    public String createNewYmlRules(String ruleFileName) throws Exception {
        int cnt = 0;
        Rules rules = new Rules();
        Rules myRules = reader.getRulesFromYml(ruleFileName);

        for (Rule rule : myRules) {
            rules.register(rule);
            cnt++;
        }

        addRules(ruleFileName, rules); //todo
        System.out.println("создано правил из " + ruleFileName + ": " + cnt);
        return "создано правил из " + ruleFileName + ": " + cnt;
    }

    @Override
    public Facts getFactsFromModels(List<T> models) {
        Facts localFacts = new Facts();

        models.forEach(model -> {
            localFacts.put(model.getId(), model);
            facts.put(model.getId(), model);
        });

        return localFacts;
    }

    @Override
    public String createCompositeRule(CompositeType compositeType, List<String> ruleNames, String nameRule, String description, int priority) {

        CompositeRule myRuleGroup = factoryCompositeRule(compositeType, nameRule, description, priority);

        //todo
        //for example
        ruleNames.add("rule1");
        ruleNames.add("rule3");

        getRules().forEach(cRules -> {
            cRules.forEach(rule -> {
                if (ruleNames.contains(rule.getName())) {
                    myRuleGroup.addRule(rule);
                }
            });
        });

        Rules rules = new Rules();
        rules.register(myRuleGroup);

        addRules(myRuleGroup.getName(), rules);

        return "added " + myRuleGroup.getName();
    }

    private CompositeRule factoryCompositeRule(CompositeType compositeType, String nameRule, String description, int priority) {
        CompositeRule compositeRule;
        switch (compositeType) {
            case ActivationRuleGroup:
                compositeRule = new ActivationRuleGroup(nameRule, description, priority);
                break;
            case ConditionalRuleGroup:
                compositeRule = new ConditionalRuleGroup(nameRule, description, priority);
                break;
            case UnitRuleGroup:
                compositeRule = new UnitRuleGroup(nameRule, description, priority);
                break;
            default:
                throw new IllegalArgumentException("Wrong composite type: " + compositeType);
        }

        return compositeRule;
    }
}
