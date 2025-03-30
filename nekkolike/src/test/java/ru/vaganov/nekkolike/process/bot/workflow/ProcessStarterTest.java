package ru.vaganov.nekkolike.process.bot.workflow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProcessStarterTest {

    @Autowired
    private ProcessStarter starter;

    @Test
    void a(){
        starter.startProcess("hello");
    }

}