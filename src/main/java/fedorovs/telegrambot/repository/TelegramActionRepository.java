package fedorovs.telegrambot.repository;

import fedorovs.telegrambot.entities.TelegramAction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TelegramActionRepository extends CrudRepository<TelegramAction, Long> {
}
