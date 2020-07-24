package com.example.innowise.config;

import com.example.innowise.listener.MyFileChangeListener;
import com.example.innowise.listener.MyRuleListener;
import com.example.innowise.service.ReaderRuleYml;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.reader.YamlRuleDefinitionReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public ReaderRuleYml readerRuleYml() {
        return new ReaderRuleYml();
    }

    @Bean
    public RulesEngine rulesEngine() {
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.registerRuleListener(myRuleListener());
        return rulesEngine;
    }

    @Bean
    public MyRuleListener myRuleListener() {
        return new MyRuleListener();
    }

    @Bean
    public MVELRuleFactory ruleFactory() {
        return new MVELRuleFactory(new YamlRuleDefinitionReader());
    }

    @Bean
    public Facts facts() {
        return new Facts();
    }

    @Bean
    public Rules rules() {
        return new Rules();
    }




}
