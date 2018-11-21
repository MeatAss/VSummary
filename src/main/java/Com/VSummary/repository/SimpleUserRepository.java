package Com.VSummary.repository;

import Com.VSummary.domain.entities.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {
    SimpleUser findByUsername(String username);
    SimpleUser findByActivationCode(String activationCode);
}
