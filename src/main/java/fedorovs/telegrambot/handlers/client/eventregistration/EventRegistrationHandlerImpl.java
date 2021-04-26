package fedorovs.telegrambot.handlers.client.eventregistration;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.entities.TelegramAction;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.TelegramActionRepository;
import fedorovs.telegrambot.services.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


@Slf4j
@Component
public class EventRegistrationHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final TelegramActionRepository telegramActionRepository;

    public EventRegistrationHandlerImpl(MessageSender sender,
                                        TelegramActionRepository telegramActionRepository) {
        this.sender = sender;
        this.telegramActionRepository = telegramActionRepository;
    }

    @Override
    public void operate(OperationHandle operationHandle) {

        sender.send(createSendMessage(operationHandle.getChatId()),
                operationHandle.getBot());

        TelegramAction telegramAction = new TelegramAction();
        telegramAction.setChatId(operationHandle.getChatId());
        telegramAction.setAction(Command.CREATE_EVENT_REGISTRATION);
        telegramAction.setContext(operationHandle.getParsedCommand().getText());
        telegramActionRepository.save(telegramAction);
    }

    private SendMessage createSendMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        String text = new StringBuilder()
                .append("Введите ФИО и телефон. Пожауйста, используйте формат").append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("ФИО : телефон").append(System.lineSeparator())
                .toString();
        message.setText(text);

        return message;
    }

    @Override
    public Command getCommand() {
        return Command.EVENT_REGISTRATION;
    }
}
