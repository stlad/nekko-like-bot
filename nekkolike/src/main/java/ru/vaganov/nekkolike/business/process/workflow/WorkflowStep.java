package ru.vaganov.nekkolike.business.process.workflow;

public enum WorkflowStep {
    MENU,

    JOIN_STARTED,
    JOIN_WAIT_FOR_NAME,
    JOIN_COMPLETED,

    ADD_CAT_STARTED,
    ADD_CAT_WAIT_FOR_PHOTO,
    ADD_CAT_WAIT_FOR_NAME,
    ADD_CAT_WAIT_FOR_ACCEPT,
    ADD_CAT_ACCEPTED,
    ADD_CAT_COMPLETED,
}
