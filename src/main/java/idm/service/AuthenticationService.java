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


    @Transactional
    public void register(UserRegistrationDto userRegistrationDto) {
        User user = repositoryUser.findByUsername(userRegistrationDto.getUsername());
        if (user != null) {
            throw new BaseException("User with the given login already exists", null);
        }

        user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(userRegistrationDto.getPassword());
        user.setEmail((userRegistrationDto.getEmail()));
        user.setRoles(Role.USER);
        user.setBio(userRegistrationDto.getBio());

        //Client client = new Client(UUID.randomUUID().toString(), user);
        //repositoryClient.save(client);
        repositoryUser.save(user);
    }

    @Transactional
    public void authenticate(String login, String password, HttpServletResponse response) {
        authenticateForRoles(login, password, response,userService.findOne(login).getRoles());
    }

    private void authenticateForRoles(String login, String password,
                                      HttpServletResponse response,
                                      Role roles) {
        User user = repositoryUser.findByUsername(login);
        if (user == null /*|| !roles.contains(user.getRoles())*/) {
            throw new BaseException("User with the given login does not exist",null);
        }

        //надо разобраться, в каком формате и куда прилетает пароль
        /*
        if (!passwordEncoder.checkPassword(password, user.getPassword())) {
            throw new BaseException("Incorrect password",null);
        }
        */
        UserData userData = user.toUserData();
        jwtGenerator.encodeJwt(userData, response);
    }
    @Transactional
    public void authenticateUpdate(String login, String password, HttpServletResponse response){
        User user = repositoryUser.findByUsername(login);
        UserData userData = user.toUserData();
        jwtGenerator.encodeJwt(userData, response);
    }
}
