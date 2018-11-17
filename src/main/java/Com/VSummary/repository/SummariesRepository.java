package Com.VSummary.repository;

import Com.VSummary.domain.entities.Summaries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummariesRepository extends JpaRepository<Summaries, Long> {
    Summaries findByNameSummary(String nameSummary);
}
