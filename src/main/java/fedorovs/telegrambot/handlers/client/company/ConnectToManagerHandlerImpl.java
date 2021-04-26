package fedorovs.telegrambot.handlers.client.company;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.services.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;

@Slf4j
@Component
public class ConnectToManagerHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;

    public ConnectToManagerHandlerImpl(MessageSender sender,
                                       @Value("${manger.firstName}") String firstName,
                                       @Value("${manger.lastName}") String lastName,
                                       @Value("${manger.phoneNumber}") String phoneNumber) {
        this.sender = sender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        SendContact sendContact = new SendContact();
        sendContact.setChatId(String.valueOf(operationHandle.getChatId()));
        sendContact.setFirstName(firstName);
        sendContact.setLastName(lastName);
        sendContact.setPhoneNumber(phoneNumber);
        sender.send(sendContact, operationHandle.getBot());
    }

    @Override
    public Command getCommand() {
        return Command.CONNECT_TO_MANAGER;
    }
}
