//package Com.VSummary.repository;
//
//import Com.VSummary.domain.entities.MySQL.TagsTest;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface TagsTestRepostiroty extends JpaRepository<TagsTest, Long> {
//
//    @Query(value = "select t.tagId from TagsTest t group by t.tagId")
//    List<?> findCount();
//}
