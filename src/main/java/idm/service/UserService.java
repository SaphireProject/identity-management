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

    //@Override
    /*public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repositoryUser.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }


/*
    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
*/
    //@Override
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
        LOGGER.info("user found");
        User user1=findOne(userUpdate.getUsername());
        if(user != null & user1 == null) {
            user.setEmail(userUpdate.getEmail());
            user.setUsername(userUpdate.getUsername());
            //user.setPassword(userDto.getPassword());
            repositoryUser.save(user);
            LOGGER.info("success update");
        }
        else{
            throw new BaseException("user with this login already exists",null);
        }
        return userUpdate;
    }
}
