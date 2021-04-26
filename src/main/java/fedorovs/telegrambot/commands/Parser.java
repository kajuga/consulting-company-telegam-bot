package fedorovs.telegrambot.commands;

import fedorovs.telegrambot.entities.TelegramAction;
import fedorovs.telegrambot.repository.TelegramActionRepository;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class Parser {

    private final String PREFIX_FOR_COMMAND = "/";
    private final String DELIMITER_COMMAND_BOTNAME = "@";
    private final TelegramActionRepository telegramActionRepository;

    public Parser(TelegramActionRepository telegramActionRepository) {
        this.telegramActionRepository = telegramActionRepository;
    }

    public ParsedCommand getParsedCommand(String text, Long chartId) {
        String trimText = "";
        if (text != null) trimText = text.trim();
        ParsedCommand result = new ParsedCommand(Command.NONE, trimText, null);

        if ("".equals(trimText)) return result;
        Pair<String, String> commandAndText = getDelimitedCommandFromText(trimText);
        if (isCommand(commandAndText.getKey())) {
            String commandForParse = cutCommandFromFullText(commandAndText.getKey());
            Command commandFromText = getCommandFromText(commandForParse);
            result.setText(commandAndText.getValue());
            result.setCommand(commandFromText);
        } else {
            Optional<TelegramAction> lastCommand = telegramActionRepository.findById(chartId);
            if (lastCommand.isPresent()) {
                ParsedCommand parsedCommand = new ParsedCommand();
                parsedCommand.setCommand(lastCommand.get().getAction());
                parsedCommand.setContext(lastCommand.get().getContext());
                parsedCommand.setText(text);
                return parsedCommand;
            }
        }
        return result;
    }

    private String cutCommandFromFullText(String text) {
        return text.contains(DELIMITER_COMMAND_BOTNAME) ?
                text.substring(1, text.indexOf(DELIMITER_COMMAND_BOTNAME)) :
                text.substring(1);
    }

    private Command getCommandFromText(String text) {
        String upperCaseText = text.toUpperCase().trim();
        Command command = Command.NONE;
        try {
            command = Command.valueOf(upperCaseText);
        } catch (IllegalArgumentException e) {
            log.debug("Can't parse command: " + text);
        }
        return command;
    }

    private Pair<String, String> getDelimitedCommandFromText(String trimText) {
        Pair<String, String> commandText;

        if (trimText.contains(" ")) {
            int indexOfSpace = trimText.indexOf(" ");
            commandText = new ImmutablePair<>(trimText.substring(0, indexOfSpace), trimText.substring(indexOfSpace + 1));
        } else commandText = new ImmutablePair<>(trimText, "");
        return commandText;
    }

    private boolean isCommand(String text) {
        return text.startsWith(PREFIX_FOR_COMMAND);
    }
}
