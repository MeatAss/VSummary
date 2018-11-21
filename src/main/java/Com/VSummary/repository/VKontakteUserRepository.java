package Com.VSummary.repository;

import Com.VSummary.domain.entities.VKontakteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VKontakteUserRepository extends JpaRepository<VKontakteUser, Long> {
    VKontakteUser findByUserId(String userId);
}
