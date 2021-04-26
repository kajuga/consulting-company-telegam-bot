package fedorovs.telegrambot.services;

import fedorovs.telegrambot.commands.ParsedCommand;
import fedorovs.telegrambot.commands.Parser;
import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageReceiver {

    private final Map<Command, AbstractHandler> handlers = new HashMap();
    private final Parser parser;

    public MessageReceiver(List<AbstractHandler> handlers, Parser parser) {
        this.parser = parser;
        for (AbstractHandler handler : handlers) {
           this.handlers.put(handler.getCommand(), handler);
       }
    }

    @Async
    public void receive(Update update, TelegramLongPollingBot bot){
        String inputText;
        Long chartId;
        Integer userId;
        if (update.getCallbackQuery() != null) {
            inputText = update.getCallbackQuery().getData();
            chartId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
        } else {
            inputText = update.getMessage().getText();
            chartId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
        }
        ParsedCommand parsedCommand = parser.getParsedCommand(inputText, chartId);
        OperationHandle operationHandle = new OperationHandle();
        operationHandle.setBot(bot);
        operationHandle.setChatId(chartId);
        operationHandle.setUserId(userId);
        operationHandle.setParsedCommand(parsedCommand);
        handlers.get(parsedCommand.getCommand()).operate(operationHandle);
    }
}
