package fedorovs.telegrambot.handlers;

import fedorovs.telegrambot.commands.Command;

public interface AbstractHandler {

    void operate(OperationHandle operationHandle);
    Command getCommand();
}
