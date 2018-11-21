package Com.VSummary.repository;

import Com.VSummary.domain.entities.TwitterUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitterUserRepository extends JpaRepository<TwitterUser, Long> {
    TwitterUser findByProfileId(long profileId);
}
