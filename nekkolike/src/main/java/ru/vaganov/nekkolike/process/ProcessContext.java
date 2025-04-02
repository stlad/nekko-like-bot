package ru.vaganov.nekkolike.process;

import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.workflow.StartState;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessContext {

    private final Map<Long, ProcessInstance> processes = new HashMap<>();

    public ProcessInstance initProcess(Long id, ProcessState state) {
        var process = new ProcessInstance(id, null, state);
        processes.put(id, process);
        return process;
    }

    public ProcessInstance getProcess(Long id){
        if(!processes.containsKey(id)){
            initProcess(id, new StartState());
        }
        return processes.get(id);
    }

}
