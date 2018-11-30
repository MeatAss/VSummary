package Com.VSummary.repository;

import Com.VSummary.domain.entities.MySQL.SummaryTags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SummaryTagsRepository extends JpaRepository<SummaryTags, Long> {
    List<SummaryTags> findAllByTagIn(List<String> tag);
}