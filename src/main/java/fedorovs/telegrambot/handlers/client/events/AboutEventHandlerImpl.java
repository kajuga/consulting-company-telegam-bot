package fedorovs.telegrambot.handlers.client.events;

import fedorovs.telegrambot.commands.ParsedCommand;
import fedorovs.telegrambot.entities.Event;
import fedorovs.telegrambot.entities.Speaker;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.EventRepository;
import fedorovs.telegrambot.services.MessageSender;
import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.util.Icon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AboutEventHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final EventRepository eventRepository;

    public AboutEventHandlerImpl(MessageSender sender,
                                 EventRepository eventRepository) {
        this.sender = sender;
        this.eventRepository = eventRepository;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        sender.send(createSendMessage(operationHandle.getChatId(), operationHandle.getParsedCommand()),
                operationHandle.getBot());
    }

    private SendMessage createSendMessage(Long chatId, ParsedCommand parsedCommand) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        Optional<Event> event = eventRepository.findById(Long.valueOf(parsedCommand.getText()));
        if (event.isPresent()) {
            StringBuilder text = new StringBuilder()
                    .append(event.get().getTitle()).append(":").append(System.lineSeparator())
                    .append(event.get().getDescription()).append(System.lineSeparator())
                    .append("Дата: ").append(event.get().getDateTime()).append(System.lineSeparator())
                    .append("Длительность: ").append(event.get().getDuration()).append(System.lineSeparator())
                    .append("Авторы: ").append(System.lineSeparator());
            for (Speaker speaker : event.get().getSpeakers()) {
                text.append(System.lineSeparator())
                        .append(speaker.getName()).append(System.lineSeparator())
                        .append(speaker.getCompany()).append(System.lineSeparator())
                        .append(speaker.getDescription()).append(System.lineSeparator())
                        .append(System.lineSeparator());
            }

            message.setText(text.toString());
            message.setReplyMarkup(menu(event.get().getId()));
        }
        return message;
    }

    private InlineKeyboardMarkup menu(Long serviceId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(new ArrayList<>());

        InlineKeyboardButton serviceButton = new InlineKeyboardButton();
        serviceButton.setText(Icon.CHECK.get() + "Зарегистрироваться");
        serviceButton.setCallbackData("/event_registration " + serviceId);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(serviceButton);
        markup.getKeyboard().add(row);

        return markup;
    }

    @Override
    public Command getCommand() {
        return Command.ABOUT_EVENT;
    }
}
