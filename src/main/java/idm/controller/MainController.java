package idm.controller;

import idm.data.Client;
import idm.repository.RepositoryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@ComponentScan
public class MainController {

    /*
    @Autowired
    private RepositoryClient repositoryClient;
    */

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

   /*
    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Client> clients = repositoryClient.findAll();
        model.put("clients", clients);
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String secret_id, Map<String, Object> model) {
        Client client = new Client(secret_id);
        repositoryClient.save(client);
        Iterable<Client> clients = repositoryClient.findAll();
        model.put("clients", clients);
        return "main";
    }
    */

}
