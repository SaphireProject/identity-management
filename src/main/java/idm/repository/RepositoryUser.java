package idm.repository;

import idm.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryUser extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
