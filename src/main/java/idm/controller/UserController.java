package idm.controller;


import idm.config.JwtGenerator;
import idm.data.User;
import idm.model.ApiResponse;
import idm.model.UserData;
import idm.model.UserUpdate;
import idm.service.AuthenticationService;
import idm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins="*")
public class UserController {

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
    public ApiResponse<UserData> getPage(@RequestHeader("Authorization") String request){
        //User user= userService.getUserPersonalPage();
        return new ApiResponse<>(jwtGenerator.decodeNew(request).getUserData());
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody UserUpdate userUpdate,
                                 @RequestHeader("Authorization") String request,
                                 HttpServletResponse response) {
        LOGGER.info(request);
        userService.update(userUpdate, jwtGenerator.decodeNew(request).getUserData().getId());
        authenticationService.authenticateUpdate(userUpdate.getUsername(),
                jwtGenerator.decodeNew(request).getUserData().getPassword(), response);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ApiResponse<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return new ApiResponse<>(null);
    }




}
