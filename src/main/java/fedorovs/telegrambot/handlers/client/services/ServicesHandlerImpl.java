package fedorovs.telegrambot.handlers.client.services;

import fedorovs.telegrambot.entities.Service;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.ServiceRepository;
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


@Slf4j
@Component
public class ServicesHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final ServiceRepository serviceRepository;

    public ServicesHandlerImpl(MessageSender sender,
                               ServiceRepository serviceRepository) {
        this.sender = sender;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        sender.send(createSendMessage(operationHandle.getChatId()), operationHandle.getBot());
    }

    private SendMessage createSendMessage(Long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите услугу:");
        message.setReplyMarkup(menu());
        return message;
    }

    private InlineKeyboardMarkup menu() {
        Iterable<Service> services = serviceRepository.findAll();

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(new ArrayList<>());

        for (Service service: services){
            InlineKeyboardButton serviceButton = new InlineKeyboardButton();
            serviceButton.setText(Icon.CHECK.get() + service.getTitle());
            serviceButton.setCallbackData("/about_service " + service.getId());
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(serviceButton);
            markup.getKeyboard().add(row);
        }
        return markup;
    }

    @Override
    public Command getCommand() {
        return Command.SERVICES;
    }
}
