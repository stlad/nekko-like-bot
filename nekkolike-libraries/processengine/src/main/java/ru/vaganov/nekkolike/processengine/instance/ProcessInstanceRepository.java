package ru.vaganov.nekkolike.processengine.instance;

import java.util.Optional;

public interface ProcessInstanceRepository {

    Optional<ProcessInstance> findById(Long id);

    ProcessInstance save(ProcessInstance processInstance);

    Optional<ProcessInstance> findAnyProcessToRun();

}
