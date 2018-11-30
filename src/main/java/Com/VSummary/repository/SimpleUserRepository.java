package Com.VSummary.repository;

import Com.VSummary.domain.entities.MySQL.SimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleUserRepository extends JpaRepository<SimpleUser, Long> {
    SimpleUser findByLogin(String login);
    SimpleUser findByActivationCode(String activationCode);
}
