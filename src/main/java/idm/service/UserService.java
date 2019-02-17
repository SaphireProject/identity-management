package idm.service;


import idm.data.Role;
import idm.data.User;
import idm.model.UserDto;
import idm.repository.RepositoryClient;
import idm.repository.RepositoryUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryClient repositoryClient;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositoryUser.findByUsername(username);
    }


    public boolean addUser(User user) {
        User userFromDb = repositoryUser.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        repositoryUser.save(user);
        //Client client = new Client(UUID.randomUUID().toString(), user);
       // repositoryClient.save(client);

        return true;
    }


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
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return repositoryUser.save(newUser);
    }

    public UserDto update(UserDto userDto) {
        User user = findById(userDto.getId());
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password");
            repositoryUser.save(user);
        }
        return userDto;
    }
}
