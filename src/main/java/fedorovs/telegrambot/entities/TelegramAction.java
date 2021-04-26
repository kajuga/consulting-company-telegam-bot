package fedorovs.telegrambot.entities;

import fedorovs.telegrambot.commands.Command;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "tbot", name = "telegram_action")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramAction {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private Command action;

    @Column(name = "context")
    private String context;
}
