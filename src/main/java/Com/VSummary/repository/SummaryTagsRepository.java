package Com.VSummary.repository;

import Com.VSummary.domain.entities.SummaryTags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryTagsRepository extends JpaRepository<SummaryTags, Long> {
}
