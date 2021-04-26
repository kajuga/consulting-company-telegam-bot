package fedorovs.telegrambot.handlers.client.services;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.commands.ParsedCommand;
import fedorovs.telegrambot.entities.Service;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.ServiceRepository;
import fedorovs.telegrambot.services.MessageSender;
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
public class AboutServiceHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final ServiceRepository serviceRepository;

    public AboutServiceHandlerImpl(MessageSender sender,
                                   ServiceRepository serviceRepository) {
        this.sender = sender;
        this.serviceRepository = serviceRepository;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        sender.send(createSendMessage(operationHandle.getChatId(), operationHandle.getParsedCommand()),
                operationHandle.getBot());
    }

    private SendMessage createSendMessage(Long chatId, ParsedCommand parsedCommand){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        Optional<Service> service = serviceRepository.findById(Long.valueOf(parsedCommand.getText()));
        if (service.isPresent()) {
            String text = new StringBuilder()
                    .append(service.get().getTitle()).append(":").append(System.lineSeparator())
                    .append(service.get().getDescription()).append(System.lineSeparator())
                    .append("Цена: ").append(service.get().getPrice())
                    .toString();
            message.setText(text);
            message.setReplyMarkup(menu(service.get().getId()));
        }
        return message;
    }

    private InlineKeyboardMarkup menu(Long serviceId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(new ArrayList<>());

        InlineKeyboardButton serviceButton = new InlineKeyboardButton();
        serviceButton.setText(Icon.CHECK.get() + "Заказать");
        serviceButton.setCallbackData("/order " + serviceId);

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(serviceButton);
        markup.getKeyboard().add(row);

        return markup;
    }

    @Override
    public Command getCommand() {
        return Command.ABOUT_SERVICE;
    }
}
