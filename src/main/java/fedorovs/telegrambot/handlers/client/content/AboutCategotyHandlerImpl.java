package fedorovs.telegrambot.handlers.client.content;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.commands.ParsedCommand;
import fedorovs.telegrambot.entities.Category;
import fedorovs.telegrambot.entities.UsefulContent;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.CategoryRepository;
import fedorovs.telegrambot.repository.UsefulContentRepository;
import fedorovs.telegrambot.services.MessageSender;
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
public class AboutCategotyHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final UsefulContentRepository usefulContentRepository;
    private final CategoryRepository categoryRepository;


    public AboutCategotyHandlerImpl(MessageSender sender,
                                    UsefulContentRepository usefulContentRepository,
                                    CategoryRepository categoryRepository) {
        this.sender = sender;
        this.usefulContentRepository = usefulContentRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        sender.send(createSendMessage(operationHandle.getChatId(), operationHandle.getParsedCommand()),
                operationHandle.getBot());
    }

    private SendMessage createSendMessage(Long chatId, ParsedCommand parsedCommand){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        Optional<Category> category = categoryRepository.findById(Long.valueOf(parsedCommand.getText()));

        if (category.isPresent()) {
            message.setText(category.get().getTitle());
            message.setReplyMarkup(menu(Long.valueOf(parsedCommand.getText())));
        }
        return message;
    }

    private InlineKeyboardMarkup menu(Long categoryId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(new ArrayList<>());

        List<UsefulContent> usefulContents = usefulContentRepository.findUsefulContentsByCategory_Id(categoryId);
        for (UsefulContent content: usefulContents){

            InlineKeyboardButton serviceButton = new InlineKeyboardButton();
            serviceButton.setText(content.getTitle());
            serviceButton.setUrl(content.getUrl());

            List<InlineKeyboardButton> row = new ArrayList<>();
            markup.getKeyboard().add(row);
            row.add(serviceButton);
        }

        return markup;
    }

    @Override
    public Command getCommand() {
        return Command.ABOUT_СATEGORY;
    }
}
