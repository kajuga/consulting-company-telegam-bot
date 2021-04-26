package fedorovs.telegrambot.handlers;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.services.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
public class StartHandlerImpl implements AbstractHandler {

    private final MessageSender sender;

    public StartHandlerImpl(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(operationHandle.getChatId()));
        message.setText("Добро пожаловать!");
        sender.send(message, operationHandle.getBot());
    }

    @Override
    public Command getCommand() {
        return Command.START;
    }
}
