package idm.repository;

import idm.data.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryUser extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
