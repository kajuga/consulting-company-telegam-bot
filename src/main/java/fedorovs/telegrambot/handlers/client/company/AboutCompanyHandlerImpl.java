package fedorovs.telegrambot.handlers.client.company;

import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.util.Icon;
import fedorovs.telegrambot.services.MessageSender;
import fedorovs.telegrambot.commands.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AboutCompanyHandlerImpl implements AbstractHandler {

    private final MessageSender sender;

    public AboutCompanyHandlerImpl(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(operationHandle.getChatId()));
        message.setText("Выберите пункт меню:");
        message.setReplyMarkup(menu());
        sender.send(message, operationHandle.getBot());
    }

    private InlineKeyboardMarkup menu() {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(new ArrayList<>());

        InlineKeyboardButton servicesButton = new InlineKeyboardButton();
        servicesButton.setText(Icon.CHECK.get() + "Услуги");
        servicesButton.setCallbackData("/services");

        InlineKeyboardButton connectToManagerButton = new InlineKeyboardButton();
        connectToManagerButton.setText(Icon.CHECK.get() + "Связь с менеджером");
        connectToManagerButton.setCallbackData("/connect_to_manager");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(servicesButton);
        row1.add(connectToManagerButton);
        markup.getKeyboard().add(row1);
        return markup;
    }

    @Override
    public Command getCommand() {
        return Command.ABOUT_COMPANY;
    }
}
