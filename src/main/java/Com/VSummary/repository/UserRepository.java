package Com.VSummary.repository;

import Com.VSummary.domain.entities.MySQL.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String Login);
    List<User> findAllByIdNotIn(long userId);
}