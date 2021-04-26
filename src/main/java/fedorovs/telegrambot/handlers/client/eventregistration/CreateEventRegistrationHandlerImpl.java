package fedorovs.telegrambot.handlers.client.eventregistration;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.entities.Event;
import fedorovs.telegrambot.entities.EventRegistration;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.EventRegistrationRepository;
import fedorovs.telegrambot.repository.EventRepository;
import fedorovs.telegrambot.services.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;


@Slf4j
@Component
public class CreateEventRegistrationHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final EventRepository eventRepository;
    private final EventRegistrationRepository eventRegistrationRepository;

    public CreateEventRegistrationHandlerImpl(MessageSender sender,
                                              EventRepository eventRepository,
                                              EventRegistrationRepository eventRegistrationRepository) {
        this.sender = sender;
        this.eventRepository = eventRepository;
        this.eventRegistrationRepository = eventRegistrationRepository;
    }

    @Override
    @Transactional
    public void operate(OperationHandle operationHandle) {
        String[] data = operationHandle.getParsedCommand().getText().split(" : ");
        Optional<Event> event = eventRepository.findById(Long.valueOf(operationHandle.getParsedCommand().getContext()));

        SendMessage sendMessage;

        try {
            if (event.isPresent()) {
                EventRegistration eventRegistration = new EventRegistration();
                eventRegistration.setEvent(event.get());
                eventRegistration.setUserName(data[0]);
                eventRegistration.setUserPhone(data[1]);
                eventRegistration = eventRegistrationRepository.save(eventRegistration);
                sendMessage = createSuccessSendMessage(operationHandle.getChatId(), eventRegistration);
            } else {
                sendMessage = createErrorSendMessage(operationHandle.getChatId());
            }
        } catch (Exception e) {
            log.error("Error of creating event registration", e);
            sendMessage = createErrorSendMessage(operationHandle.getChatId());
        }

        sender.send(sendMessage, operationHandle.getBot());
    }

    private SendMessage createSuccessSendMessage(Long chatId, EventRegistration eventRegistration) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        String text = new StringBuilder()
                .append("Регистрация на событие №").append(eventRegistration.getId()).append(" успешно создана.").append(System.lineSeparator())
                .append("Событие: ").append(eventRegistration.getEvent().getTitle()).append(";").append(System.lineSeparator())
                .append("ФИО: ").append(eventRegistration.getUserName()).append(";").append(System.lineSeparator())
                .append("Номер телефона: ").append(eventRegistration.getUserPhone()).append(";").append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Менеджер свяжется с вами в ближайшее время.").append(System.lineSeparator())
                .toString();
        message.setText(text);


        return message;
    }

    private SendMessage createErrorSendMessage(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Ошибка регистрации на событие.");
        return message;
    }

    @Override
    public Command getCommand() {
        return Command.CREATE_EVENT_REGISTRATION;
    }
}
