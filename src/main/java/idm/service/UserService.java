package idm.service;


import idm.data.User;
import idm.exception.BaseException;
import idm.model.UserDto;
import idm.model.UserUpdate;
import idm.repository.RepositoryClient;
import idm.repository.RepositoryUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service//(value = "userService")
public class UserService/* implements UserDetailsService*/ {
    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryClient repositoryClient;

    @Autowired
    private AuthenticationService authenticationService;

    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        repositoryUser.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public User findById(long id) {
        Optional<User> optionalUser = repositoryUser.findById(id);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }

    @Transactional
    public User getUserById(Long id) {
        User user = repositoryUser.getOne(id);
        if (user == null) {
            throw new BaseException("User not found",null);
        }
        return user;
    }

   // @Override
    public User findOne(String username) {
        return repositoryUser.findByUsername(username);
    }

    //@Override
    public void delete(long id) {
        repositoryUser.deleteById(id);
    }

    public User findByEmail(String email){return repositoryUser.findByEmail(email);}

   // @Override
    public User save(UserDto user) {
        User newUser = new User();
      //  newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        return repositoryUser.save(newUser);
    }

    //@Override
    /**
     * Получаем новые данные и айдишник из токена, возвращем хз что, наверно те же данные
     *
     * */
    public UserUpdate update(UserUpdate userUpdate, Long id) {
        User user = findById(id);
        LOGGER.info("user with this id found");
        User userUsername=findOne(userUpdate.getUsername());
        User userEmail = findByEmail(userUpdate.getEmail());

        if(user != null
                & userUsername !=null
                & !user.getUsername().equals(userUpdate.getUsername())
                & userEmail !=null
                & !user.getEmail().equals(userUpdate.getEmail())){
            throw new BaseException("user with login and email already exists",null);
        }

        if(user != null
                & userUsername !=null
                & !user.getUsername().equals(userUpdate.getUsername())){
            throw new BaseException("user with login already exists",null);
        }

        if(user != null
                & userEmail !=null
                & !user.getEmail().equals(userUpdate.getEmail())){
            throw new BaseException("user with email already exists",null);
        }

        user.setUsername(userUpdate.getUsername());
        user.setEmail(userUpdate.getEmail());
        user.setBio(userUpdate.getBio());

/*
        if(user != null & user.getUsername().equals(userUpdate.getUsername()) & userEmail==null) {

            LOGGER.info("username doesn't change");
            user.setEmail(userUpdate.getEmail());
            user.setBio(userUpdate.getBio());
            repositoryUser.save(user);
            LOGGER.info("successful email and bio update");
        }
        else if(user != null & userUsername==null & userEmail==null){
            user.setUsername(userUpdate.getUsername());
            user.setEmail(userUpdate.getEmail());
            user.setBio(userUpdate.getBio());
            repositoryUser.save(user);
            LOGGER.info("successful email, bio and username update");
        }
        else if(user != null & ){

        }
        else{
            throw new BaseException("user with this login already exists",null);
        }
        */
        return userUpdate;
    }
}
