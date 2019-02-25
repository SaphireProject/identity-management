package idm.service;


import idm.data.User;
import idm.model.UserDto;
import idm.repository.RepositoryClient;
import idm.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryClient repositoryClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repositoryUser.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    /*
    public boolean addUser(User user) {
        User userFromDb = repositoryUser.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

       // user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        repositoryUser.save(user);
        //Client client = new Client(UUID.randomUUID().toString(), user);
       // repositoryClient.save(client);

        return true;
    }
    */


    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        repositoryUser.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    public User findById(long id) {
        Optional<User> optionalUser = repositoryUser.findById(id);
        return optionalUser.isPresent() ? optionalUser.get() : null;
    }

    public User findOne(String username) {
        return repositoryUser.findByUsername(username);
    }

    public void delete(long id) {
        repositoryUser.deleteById(id);
    }

    public User save(UserDto user) {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setEmail(user.getEmail());
        return repositoryUser.save(newUser);
    }

    @Transactional
    public UserDto update(UserDto userDto, long id) {
        User user = findById(id);
        if(user != null) {
            user.setId(id);
            user.setEmail(userDto.getEmail());
            user.setUsername(userDto.getUsername());
            user.setPassword(userDto.getPassword());
            repositoryUser.save(user);
        }
        return userDto;
    }
}
