package idm.service;

import idm.data.Role;
import idm.data.User;
import idm.exception.BaseException;
import idm.model.UserDto;
import idm.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

public class AuthenticationService {

    @Autowired
    private RepositoryUser repositoryUser;

    @Transactional
    public void register(UserDto userDto) {
        User user = repositoryUser.findByUsername(userDto.getUsername());
        if (user != null) {
            throw new BaseException("User with the given login already exists", null);
        }

        user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRoles(Collections.singleton(Role.USER));

        repositoryUser.save(user);
    }

}
