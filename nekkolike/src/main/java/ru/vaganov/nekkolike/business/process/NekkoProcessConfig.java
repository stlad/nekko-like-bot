package ru.vaganov.nekkolike.business.process;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vaganov.nekkolike.business.process.workflow.NekkoProcessStep;
import ru.vaganov.nekkolike.processengine.ProcessEngine;
import ru.vaganov.nekkolike.processengine.ProcessInstanceRepository;

import java.util.List;

@Configuration
public class NekkoProcessConfig {

    @Bean
    public ProcessEngine processEngine(List<NekkoProcessStep> steps,
                                       ProcessInstanceRepository processInstanceRepository) {
        var engine = new ProcessEngine(processInstanceRepository);
        steps.forEach(step -> engine.registerStep(step.getState(), step));
        return engine;
    }
}
