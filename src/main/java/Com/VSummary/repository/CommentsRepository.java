package Com.VSummary.repository;

import Com.VSummary.domain.entities.MySQL.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
