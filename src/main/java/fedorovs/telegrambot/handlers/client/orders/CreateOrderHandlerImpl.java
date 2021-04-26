package fedorovs.telegrambot.handlers.client.orders;

import fedorovs.telegrambot.commands.Command;
import fedorovs.telegrambot.entities.Order;
import fedorovs.telegrambot.entities.OrderStatus;
import fedorovs.telegrambot.handlers.AbstractHandler;
import fedorovs.telegrambot.handlers.OperationHandle;
import fedorovs.telegrambot.repository.OrderRepository;
import fedorovs.telegrambot.repository.ServiceRepository;
import fedorovs.telegrambot.services.MessageSender;
import fedorovs.telegrambot.entities.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;


@Slf4j
@Component
public class CreateOrderHandlerImpl implements AbstractHandler {

    private final MessageSender sender;
    private final ServiceRepository serviceRepository;
    private final OrderRepository orderRepository;

    public CreateOrderHandlerImpl(MessageSender sender,
                                  ServiceRepository serviceRepository,
                                  OrderRepository orderRepository) {
        this.sender = sender;
        this.serviceRepository = serviceRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public void operate(OperationHandle operationHandle) {
        String[] data = operationHandle.getParsedCommand().getText().split(" : ");
        Optional<Service> service =  serviceRepository.findById(Long.valueOf(operationHandle.getParsedCommand().getContext()));

        SendMessage sendMessage;

        try {
            if (service.isPresent()) {
                Order order = new Order();
                order.setService(service.get());
                order.setStatus(OrderStatus.CREATED);
                order.setUserName(data[0]);
                order.setUserPhone(data[1]);
                order = orderRepository.save(order);
                sendMessage = createSuccessSendMessage(operationHandle.getChatId(), order);
            } else {
                sendMessage = createErrorSendMessage(operationHandle.getChatId());
            }
        } catch (Exception e) {
            log.error("Error of creating order", e);
            sendMessage = createErrorSendMessage(operationHandle.getChatId());
        }

        sender.send(sendMessage, operationHandle.getBot());
    }

    private SendMessage createSuccessSendMessage(Long chatId, Order order){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));

        String text = new StringBuilder()
                .append("Заказ №").append(order.getId()).append(" успешно создан.").append(System.lineSeparator())
                .append("Услуга: ").append(order.getService().getTitle()).append(";").append(System.lineSeparator())
                .append("Цена: ").append(order.getService().getPrice()).append(";").append(System.lineSeparator())
                .append("ФИО: ").append(order.getUserName()).append(";").append(System.lineSeparator())
                .append("Номер телефона: ").append(order.getUserPhone()).append(";").append(System.lineSeparator())
                .append(System.lineSeparator())
                .append("Менеджер свяжется с вами в ближайшее время.").append(System.lineSeparator())
                .toString();
        message.setText(text);


        return message;
    }

    private SendMessage createErrorSendMessage(Long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Ошибка создания заказа.");
        return message;
    }

    @Override
    public Command getCommand() {
        return Command.CREATE_ORDER;
    }
}
