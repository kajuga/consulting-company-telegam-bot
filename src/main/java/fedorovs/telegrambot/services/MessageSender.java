package fedorovs.telegrambot.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class MessageSender {

    @Async
    public  void send(BotApiMethod message, TelegramLongPollingBot bot){
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error(String.format("Error: %s", message), e);
        }
    }

}
