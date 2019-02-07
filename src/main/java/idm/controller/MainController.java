package idm.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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


    @GetMapping("/main")
    public String main(Map<String, Object> model) {

        return "main";
    }
    /*
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
