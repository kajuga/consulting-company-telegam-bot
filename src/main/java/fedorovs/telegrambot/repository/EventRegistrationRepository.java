package fedorovs.telegrambot.repository;

import fedorovs.telegrambot.entities.EventRegistration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface EventRegistrationRepository extends CrudRepository<EventRegistration, Long> {

    @Query("select er from EventRegistration er where er.event.status = fedorovs.telegrambot.entities.EventStatus.NEW"
            )
    List<EventRegistration> findEventRegistrationOnNewEvents();
}
