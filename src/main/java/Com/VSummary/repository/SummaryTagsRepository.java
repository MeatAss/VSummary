package Com.VSummary.repository;

import Com.VSummary.domain.SummaryTags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryTagsRepository extends JpaRepository<SummaryTags, Long> {
}
