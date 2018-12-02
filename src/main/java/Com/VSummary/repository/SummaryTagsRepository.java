package Com.VSummary.repository;

import Com.VSummary.domain.CloudTag;
import Com.VSummary.domain.entities.MySQL.SummaryTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface SummaryTagsRepository extends JpaRepository<SummaryTags, Long> {
    Set<SummaryTags> findAllByTagIn(List<String> tag);

//    @Query("SELECT " +
//            "    new com.path.to.CloudTags(v.summary_tags, COUNT(v.summary_tags)) " +
//            "FROM " +
//            "    summary_tags v " +
//            "GROUP BY " +
//            "    v.summary_tags");
//    @Query("select new com.path.to.CloudTags(Count(v.summary_tags), v.summary_tags) from summary_tags v group by v.summary_tags")
//    List<CloudTag> findCloudTag();


}