package Com.VSummary.repository;


import Com.VSummary.domain.entities.MySQL.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
}
