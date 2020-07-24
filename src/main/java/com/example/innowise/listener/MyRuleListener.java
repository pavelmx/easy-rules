package com.example.innowise.listener;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;


public class MyRuleListener implements RuleListener {

    @Override
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        System.out.println("before evaluate");
        return true;
    }

    @Override
    public void afterEvaluate(Rule rule, Facts facts, boolean evaluationResult) {
        System.out.println("after evaluate. result: " + evaluationResult);
    }

    @Override
    public void beforeExecute(Rule rule, Facts facts) {
        System.out.println("before execute");
    }

    @Override
    public void onSuccess(Rule rule, Facts facts) {
        System.out.println("on success");
    }
}
