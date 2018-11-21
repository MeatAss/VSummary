package Com.VSummary.repository;

import Com.VSummary.domain.entities.FacebookUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacebookUserRepository extends JpaRepository<FacebookUser, Long> {
    FacebookUser findByEmail(String email);
}
