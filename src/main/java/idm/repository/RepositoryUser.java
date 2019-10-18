package idm.repository;

import idm.data.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * RepositoryUser interface for repository with user
 */
@Repository
@ComponentScan
public interface RepositoryUser extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
    User getOne(Long id);
    User findByEmail(String email);
}
