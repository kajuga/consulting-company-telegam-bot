package fedorovs.telegrambot.handlers.client.content;

import fedorovs.telegrambot.entities.Category;
import fedorovs.telegrambot.entities.Service;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.CategoryRepository;
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
public class UsefulContentHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final CategoryRepository categoryRepository;

    public UsefulContentHandlerImpl(MessageSender sender,
                                    CategoryRepository categoryRepository) {
        this.sender = sender;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        sender.send(createSendMessage(operationHandle.getChatId()), operationHandle.getBot());
    }

    private SendMessage createSendMessage(Long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Выберите категорию:");
        message.setReplyMarkup(menu());
        return message;
    }

    private InlineKeyboardMarkup menu() {
        Iterable<Category> categories = categoryRepository.findAll();

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(new ArrayList<>());

        for (Category category: categories){
            InlineKeyboardButton serviceButton = new InlineKeyboardButton();
            serviceButton.setText(Icon.CHECK.get() + category.getTitle());
            serviceButton.setCallbackData("/about_сategory " + category.getId());
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(serviceButton);
            markup.getKeyboard().add(row);
        }
        return markup;
    }

    @Override
    public Command getCommand() {
        return Command.USEFUL_CONTENT;
    }
}
