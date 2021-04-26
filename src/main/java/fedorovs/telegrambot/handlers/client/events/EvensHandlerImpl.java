package fedorovs.telegrambot.handlers.client.events;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.entities.Event;
import fedorovs.telegrambot.entities.EventStatus;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.EventRepository;
import fedorovs.telegrambot.services.MessageSender;
import fedorovs.telegrambot.util.Icon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class EvensHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final EventRepository eventRepository;

    public EvensHandlerImpl(MessageSender sender,
                            EventRepository eventRepository) {
        this.sender = sender;
        this.eventRepository = eventRepository;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        sender.send(createSendMessage(operationHandle.getChatId()), operationHandle.getBot());
    }

    private SendMessage createSendMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите событие:");
        message.setReplyMarkup(menu());
        return message;
    }

    private InlineKeyboardMarkup menu() {
        List<Event> events = eventRepository.findEventsByStatus(EventStatus.NEW);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(new ArrayList<>());

        for (Event event : events) {
            InlineKeyboardButton serviceButton = new InlineKeyboardButton();
            serviceButton.setText(Icon.CHECK.get() + event.getTitle());
            serviceButton.setCallbackData("/about_event " + event.getId());
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(serviceButton);
            markup.getKeyboard().add(row);
        }
        return markup;
    }

    @Override
    public Command getCommand() {
        return Command.EVENTS;
    }
}
