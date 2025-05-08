package ru.vaganov.nekkolike.bot.process;

import org.springframework.stereotype.Component;
import ru.vaganov.nekkolike.bot.process.workflow.StartState;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProcessContext {

    private final Map<Long, ProcessInstance> processes = new HashMap<>();

    public ProcessInstance initProcess(Long id, ProcessState state) {
        var process = ProcessInstance.create(id, null, state);
        processes.put(id, process);
        return process;
    }

    public ProcessInstance getProcess(Long id){
        if(!processes.containsKey(id)){
            initProcess(id, new StartState());
        }
        return processes.get(id);
    }

    public boolean isInState(ProcessInstance process,  Class<?> stateType){
        if(process.getState() == null){
            return false;
        }
        return stateType.isInstance(process.getState());
    }
}
