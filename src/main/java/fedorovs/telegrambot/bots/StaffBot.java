package fedorovs.telegrambot.bots;

import fedorovs.telegrambot.services.MessageReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class StaffBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final MessageReceiver messageReceiver;

    public StaffBot(@Value("${bot.staff.name}") String botUsername,
                    @Value("${bot.staff.token}") String botToken,
                    MessageReceiver messageReceiver) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.messageReceiver = messageReceiver;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Update {}", update);
        messageReceiver.receive(update, this);
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}
