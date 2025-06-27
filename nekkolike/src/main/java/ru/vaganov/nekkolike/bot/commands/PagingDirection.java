package ru.vaganov.nekkolike.bot.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PagingDirection {
    FIRST,
    NEXT,
    PREV
}
