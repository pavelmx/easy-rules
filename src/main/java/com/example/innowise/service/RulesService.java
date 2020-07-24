package com.example.innowise.service;

import com.example.innowise.model.BaseModel;
import com.example.innowise.model.CompositeType;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RulesService<T extends BaseModel> {

    Map<String, Rules> rulesMap = new HashMap<>();

    String createNewYmlRules(String ruleFileName) throws Exception;

    void fire(Rules rules, Facts facts);

    default Collection<Rules> getRules() {
        return rulesMap.values();
    }

    default Map<String, Rules> getRulesMap() {
        return rulesMap;
    }

    default void addRules(String ruleFileName, Rules rules) {
        rulesMap.put(ruleFileName, rules);
    }

    default void deleteRules(String ruleFileName) {
        rulesMap.remove(ruleFileName);
    }

    String assignRules(List<T> models, List<String> ruleNames);

    Facts getFactsFromModels(List<T> models);

    String createCompositeRule(CompositeType compositeType, List<String> ruleNames, String nameRule, String description, int priority);

}
