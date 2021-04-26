package fedorovs.telegrambot.handlers.staff;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.entities.EventRegistration;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.EventRegistrationRepository;
import fedorovs.telegrambot.services.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Slf4j
@Component
public class StatisticsAboutCurrentEventsHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final EventRegistrationRepository eventRegistrationRepository;

    public StatisticsAboutCurrentEventsHandlerImpl(MessageSender sender,
                                                   EventRegistrationRepository eventRegistrationRepository) {
        this.sender = sender;
        this.eventRegistrationRepository = eventRegistrationRepository;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        List<EventRegistration> eventRegistrationOnNewEvents = eventRegistrationRepository.findEventRegistrationOnNewEvents();
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(operationHandle.getChatId()));

        StringBuilder text = new StringBuilder();

        for (EventRegistration eventRegistration: eventRegistrationOnNewEvents){
            text.append("Регистрация на событие №").append(eventRegistration.getId()).append(" успешно создана.").append(System.lineSeparator())
                    .append("Событие: ").append(eventRegistration.getEvent().getTitle()).append(";").append(System.lineSeparator())
                    .append("ФИО: ").append(eventRegistration.getUserName()).append(";").append(System.lineSeparator())
                    .append("Номер телефона: ").append(eventRegistration.getUserPhone()).append(";").append(System.lineSeparator())
                    .append("----------------------------------------------------").append(System.lineSeparator())
                    .append(System.lineSeparator());
        }

        message.setText(text.toString());
        sender.send(message, operationHandle.getBot());
    }

    @Override
    public Command getCommand() {
        return Command.STATISTICS_ABOUT_CURRENT_EVENTS;
    }
}
