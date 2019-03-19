package idm.controller;


import idm.config.JwtGenerator;
import idm.data.User;
import idm.model.ApiResponse;
import idm.model.AuthUserResponse;
import idm.model.UserUpdate;
import idm.service.AuthenticationService;
import idm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins="*")
public class UserController {

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JwtGenerator jwtGenerator;

    private static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResponse<List<User>> listUser(){
        return new ApiResponse<>(userService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getOne(@PathVariable int id){
        return new ApiResponse<>(userService.findById(id));
    }


    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public UserUpdate getPage(@RequestHeader("Authorization") String request){
        User user = userService.findOne(jwtGenerator.decodeNew(request).getUserData().getLogin());
        return new UserUpdate(user.getUsername(),user.getEmail(),user.getBio());
    }
//обдумать еще раз
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public AuthUserResponse update(@RequestBody UserUpdate userUpdate,
                                   @RequestHeader("Authorization") String request,
                                   HttpServletResponse response) {
        userService.update(userUpdate, jwtGenerator.decodeNew(request).getUserData().getId());
        authenticationService.authenticateUpdate(userUpdate.getUsername(),
                jwtGenerator.decodeNew(request).getUserData().getPassword(), response);
        return new AuthUserResponse(response.getHeader(AUTHORIZATION).substring(BEARER_PREFIX.length()),null,null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ApiResponse<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return new ApiResponse<>(null);
    }




}
