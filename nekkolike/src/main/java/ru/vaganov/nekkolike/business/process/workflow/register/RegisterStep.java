package ru.vaganov.nekkolike.business.process.workflow.register;

import ru.vaganov.nekkolike.business.process.workflow.Workflow;
import ru.vaganov.nekkolike.business.process.workflow.WorkflowStep;


public enum RegisterStep implements WorkflowStep {
    JOINED,
    WAIT_FOR_NAME,
    COMPLETED
    ;
    public Workflow getFlow(){
        return Workflow.REGISTER;
    }
}
