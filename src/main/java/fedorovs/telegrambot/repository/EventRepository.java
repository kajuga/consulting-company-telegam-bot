package fedorovs.telegrambot.repository;

import fedorovs.telegrambot.entities.Event;
import fedorovs.telegrambot.entities.EventStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EventRepository extends CrudRepository<Event, Long> {

    List<Event> findEventsByStatus(EventStatus status);
}
