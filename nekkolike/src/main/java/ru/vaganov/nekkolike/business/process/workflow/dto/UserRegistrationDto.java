package ru.vaganov.nekkolike.business.process.workflow.dto;

public class UserRegistrationDto {
    private final Long chatId;

    private String username;

    private CompletionState completionState;

    public UserRegistrationDto(Long chatId) {
        this.chatId = chatId;
        completionState = CompletionState.NEW;
    }

    public void setUsername(String username){
        this.username = username;
        completionState = CompletionState.IN_PROGRESS;
    }

    public void complete(){
        completionState = CompletionState.COMPLETE;
    }
}
