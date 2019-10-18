package idm.service;


import idm.data.User;
import idm.exception.BaseException;
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

/**
 * this service realize user management
 */
@Service
public class UserService {
    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryClient repositoryClient;

    @Autowired
    private AuthenticationService authenticationService;

    private static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    /**
     *
     * method returns a list of users from the database
     *
     * @return list users
     */
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        repositoryUser.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    /**
     * method return a user from the database with the requested id
     *
     * @param id user identifier
     * @return user
     */
    public User findById(long id) {
        Optional<User> optionalUser = repositoryUser.findById(id);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }


    /**
     * method return a user from the database with the requested id
     *
     * @param id user identifier
     * @return user
     */
    @Transactional
    public User getUserById(Long id) {
        User user = repositoryUser.getOne(id);
        if (user == null) {
            throw new BaseException("User not found",null);
        }
        return user;
    }

    /**
     * method return a user from the database with the requested username
     *
     * @param username username user
     * @return user
     */
    public User findOne(String username) {
        if(username.isEmpty() && !username.equals("")){
            return repositoryUser.findByUsername(username);
        }
        else{
            throw new BaseException("Username is null" , null);
        }
    }

    /**
     * method delete a user from the database with the requested id
     *
     * @param id identifier user
     */
    public void delete(long id) {
        repositoryUser.deleteById(id);
    }

    /**
     * method return a user from the database with the requested email
     *
     * @param email email user
     * @return user
     */
    public User findByEmail(String email){return repositoryUser.findByEmail(email);}


    /**
     * method allow update information about user in database
     *
     * @param userUpdate new params user, id user
     * @return user
     */
    public UserUpdate update(UserUpdate userUpdate, Long id) {
        User user = findById(id);
        LOGGER.info("user with this id found");
        User userUsername=findOne(userUpdate.getUsername());
        User userEmail = findByEmail(userUpdate.getEmail());

        if(!userUpdate.getPasswordOld().equals("null")) {
            if (!user.getPassword().equals(userUpdate.getPasswordOld())) {
                throw new BaseException("Incorrect old password" , null);
            }
        }

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

        if(userUpdate.getPasswordNew().equals("null") ) {
            user.setUsername(userUpdate.getUsername());
            user.setEmail(userUpdate.getEmail());
            user.setBio(userUpdate.getBio());
        }
        else{
            user.setPassword(userUpdate.getPasswordNew());
            user.setUsername(userUpdate.getUsername());
            user.setEmail(userUpdate.getEmail());
            user.setBio(userUpdate.getBio());
        }
        return userUpdate;
    }
}
