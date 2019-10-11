package idm.controller;


import idm.config.JwtGenerator;
import idm.data.User;
import idm.model.*;
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
    public UserDto getOne(@PathVariable int id) {
        return new UserDto(
                userService.findById(id).getUsername(),
                userService.findById(id).getEmail(),
                userService.findById(id).getBio()
        );
    }

    @RequestMapping(path = "/info", method = RequestMethod.POST)
    public UserDtoWithId getOneUsername(@RequestBody Username username) {
        User user= userService.findOne(username.getUsername());
        return new UserDtoWithId(
                username.getUsername(),
                user.getEmail(),
                user.getId()
        );
    }


    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public UserDto getPage(@RequestHeader("Authorization") String request){

        User user = userService.findOne(jwtGenerator.decodeNew(request).getUserData().getLogin());
        return new UserDto(
                user.getUsername(),
                user.getEmail(),
                user.getBio()
        );
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public AuthUserResponse update(@RequestBody UserUpdate userUpdate,
                                   @RequestHeader("Authorization") String request,
                                   HttpServletResponse response) {


        userService.update(userUpdate, jwtGenerator.decodeNew(request).getUserData().getId());

        if(userUpdate.getPasswordNew()==null){
            authenticationService.authenticateUpdate(userUpdate.getUsername(),
                jwtGenerator.decodeNew(request).getUserData().getPassword(),
                    response);
        }
        else {
            authenticationService.authenticateUpdate(userUpdate.getUsername(),
                    userUpdate.getPasswordNew(), response);
        }
        return new AuthUserResponse(
                response.getHeader(AUTHORIZATION).substring(BEARER_PREFIX.length()),
                userUpdate.getUsername(),userUpdate.getEmail(),
                jwtGenerator.decodeNew(request).getUserData().getId()
        );
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ApiResponse<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return new ApiResponse<>(null);
    }

}
