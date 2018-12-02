package Com.VSummary.repository;

import Com.VSummary.domain.entities.MySQL.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
