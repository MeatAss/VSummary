package Com.VSummary.repository;

import Com.VSummary.domain.entities.MySQL.Summaries;
import Com.VSummary.domain.entities.MySQL.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SummariesRepository extends JpaRepository<Summaries, Long> {
    List<Summaries> findAllByUser(User user);
}
