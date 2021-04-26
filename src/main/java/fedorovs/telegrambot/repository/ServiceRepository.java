package fedorovs.telegrambot.repository;

import fedorovs.telegrambot.entities.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ServiceRepository extends CrudRepository<Service, Long> {
}
