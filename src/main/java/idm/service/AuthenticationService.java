package idm.service;

import idm.data.Role;
import idm.data.User;
import idm.exception.BaseException;
import idm.model.UserRegistrationDto;
import idm.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class AuthenticationService {

    @Autowired
    private RepositoryUser repositoryUser;

    @Transactional
    public void register(UserRegistrationDto userRegistrationDto) {
        User user = repositoryUser.findByUsername(userRegistrationDto.getUsername());
        if (user != null) {
            throw new BaseException("User with the given login already exists", null);
        }

        user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(userRegistrationDto.getPassword());
        user.setRoles(Collections.singleton(Role.USER));

        repositoryUser.save(user);
    }

}
