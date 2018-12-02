package Com.VSummary.repository;

import Com.VSummary.domain.entities.MySQL.Summaries;
import Com.VSummary.domain.entities.MySQL.SummaryTags;
import Com.VSummary.domain.entities.MySQL.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SummariesRepository extends JpaRepository<Summaries, Long> {
    List<Summaries> findAllByUser(User user);

//    @Query(value = "select s.summaryTags from Summaries s group by s.summaryTags.tag")
//    List<?> findCount();
}
