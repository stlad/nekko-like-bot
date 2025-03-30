package ru.vaganov.nekkolike.process.bot.workflow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vaganov.nekkolike.processengine.ProcessEngine;
import ru.vaganov.nekkolike.processengine.ProcessStep;

import java.util.List;

@Configuration
public class ProcessConfig {

    @Bean
    public ProcessEngine processEngine(List<ProcessStep> steps) {
        var engine = new ProcessEngine();
        steps.forEach(step ->
                engine.registerStep(step)
        );
        return engine;
    }
}
