package idm.service;

import idm.config.BCryptPasswordEncoder;
import idm.config.JwtGenerator;
import idm.data.Role;
import idm.data.User;
import idm.exception.BaseException;
import idm.model.UserData;
import idm.model.UserRegistrationDto;
import idm.repository.RepositoryClient;
import idm.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * AuthenticationService class realize authentication and authorization user in system
 *
 */

@Service
public class AuthenticationService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryClient repositoryClient;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserService userService;

    /**
     * Method realize registration in system
     *
     * @param userRegistrationDto User data for registration
     */
    @Transactional
    public void register(UserRegistrationDto userRegistrationDto) {
        User user = repositoryUser.findByUsername(userRegistrationDto.getUsername());
        User userEmail=repositoryUser.findByEmail(userRegistrationDto.getEmail());

        if (user != null & userEmail != null) {
            throw new BaseException("User with the given login and email already exists", null);
        }
        if (user != null) {
            throw new BaseException("User with the given login already exists", null);
        }
        if (userEmail != null) {
            throw new BaseException("User with the given email already exists", null);
        }

        user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(userRegistrationDto.getPassword());
        user.setEmail((userRegistrationDto.getEmail()));
        user.setRoles(Role.USER);
        user.setBio(userRegistrationDto.getBio());
        repositoryUser.save(user);
    }


    /**
     * Method realize authentication in system
     *
     * @param login User login, password User password
     */
    @Transactional
    public void authenticate(String login, String password, HttpServletResponse response) {
        authenticateForRoles(login, password, response, userService.findOne(login).getRoles());
    }

    /**
     * Method is used in @authenticate method
     *
     * @param login User login, password User password
     */
    private void authenticateForRoles(String login, String password,
                                      HttpServletResponse response,
                                      Role roles) {
        User user = repositoryUser.findByUsername(login);
        if (user == null) {
            throw new BaseException("User with the given login does not exist",null);
        }

        if (!user.getPassword().equals(password)) {
            throw new BaseException("Incorrect password",null);
        }
        UserData userData = user.toUserData();
        jwtGenerator.encodeJwt(userData, response);
    }

    /**
     * Method realize authenticate updates users, so that the user does not have to re-enter data
     *
     * @param login User login, password User password
     */
    @Transactional
    public void authenticateUpdate(String login, String password, HttpServletResponse response){
        User user = repositoryUser.findByUsername(login);
        UserData userData = user.toUserData();
        jwtGenerator.encodeJwt(userData, response);
    }
}
