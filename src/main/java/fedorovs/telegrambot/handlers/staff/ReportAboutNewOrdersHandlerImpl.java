package fedorovs.telegrambot.handlers.staff;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.entities.Order;
import fedorovs.telegrambot.entities.OrderStatus;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.OrderRepository;
import fedorovs.telegrambot.services.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Slf4j
@Component
public class ReportAboutNewOrdersHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final OrderRepository orderRepository;

    public ReportAboutNewOrdersHandlerImpl(MessageSender sender,
                                           OrderRepository orderRepository) {
        this.sender = sender;
        this.orderRepository = orderRepository;
    }

    @Override
    public void operate(OperationHandle operationHandle) {
        List<Order> orders = orderRepository.findOrdersByStatus(OrderStatus.CREATED);
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(operationHandle.getChatId()));

        StringBuilder text = new StringBuilder();

        for (Order order: orders){
            text.append("Заказ №").append(order.getId()).append(System.lineSeparator())
            .append("Услуга: ").append(order.getService().getTitle()).append(";").append(System.lineSeparator())
            .append("Цена: ").append(order.getService().getPrice()).append(";").append(System.lineSeparator())
            .append("ФИО: ").append(order.getUserName()).append(";").append(System.lineSeparator())
            .append("Номер телефона: ").append(order.getUserPhone()).append(";").append(System.lineSeparator())
            .append("----------------------------------------------------").append(System.lineSeparator())
             .append(System.lineSeparator());
        }

        message.setText(text.toString());
        sender.send(message, operationHandle.getBot());
    }

    @Override
    public Command getCommand() {
        return Command.REPORT_ABOUT_NEW_ORDERS;
    }
}
