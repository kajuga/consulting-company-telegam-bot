package fedorovs.telegrambot.repository;

import fedorovs.telegrambot.entities.Speaker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SpeakerRepository extends CrudRepository<Speaker, Long> {

}
