package idm.controller;

import idm.data.Client;
import idm.data.Role;
import idm.data.User;
import idm.repository.RepositoryClient;
import idm.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private RepositoryClient repositoryClient;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = repositoryUser.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }


        Client client = new Client("1???", user);
        repositoryClient.save(client);
        user.setRoles(Collections.singleton(Role.USER));
        repositoryUser.save(user);

        return "redirect:/login";
    }
}
