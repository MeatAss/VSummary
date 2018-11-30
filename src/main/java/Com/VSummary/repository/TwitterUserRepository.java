package Com.VSummary.repository;

import Com.VSummary.domain.entities.MySQL.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterUserRepository extends JpaRepository<TwitterUser, Long> {
    TwitterUser findByUserId(long userId);
}
