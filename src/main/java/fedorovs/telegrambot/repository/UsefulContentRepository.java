package fedorovs.telegrambot.repository;

import fedorovs.telegrambot.entities.UsefulContent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UsefulContentRepository extends CrudRepository<UsefulContent, Long> {

    List<UsefulContent> findUsefulContentsByCategory_Id(Long categoryId);
}
