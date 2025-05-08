package ru.vaganov.nekkolike.business.process;

import lombok.AllArgsConstructor;
import ru.vaganov.nekkolike.bot.NekkoBot;
import ru.vaganov.nekkolike.bot.utils.SendObjectWrapper;
import ru.vaganov.nekkolike.processengine.io.OutputMessageProvider;

@AllArgsConstructor
public class NekkoOutputMessageProvider implements OutputMessageProvider<SendObjectWrapper> {

    private final NekkoBot nekkoBot;

    @Override
    public void send(SendObjectWrapper message) {
        nekkoBot.send(message);
    }
}
