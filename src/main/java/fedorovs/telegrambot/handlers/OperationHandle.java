package fedorovs.telegrambot.handlers;

import fedorovs.telegrambot.commands.ParsedCommand;
import lombok.Data;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

@Data
public class OperationHandle {
    ParsedCommand parsedCommand;
    Long chatId;
    Integer userId;
    TelegramLongPollingBot bot;
}
