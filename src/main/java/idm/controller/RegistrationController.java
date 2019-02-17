package idm.controller;

import idm.config.JwtTokenUtil;
import idm.data.User;
import idm.model.ApiResponse;
import idm.model.AuthToken;
import idm.model.LoginUser;
import idm.model.UserDto;
import idm.repository.RepositoryUser;
import idm.service.AuthenticationService;
import idm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationService authenticationService;




    @PostMapping("/registration")
    public ApiResponse<User> addUser(@RequestBody UserDto userDto) {

        authenticationService.register(userDto);

        return new ApiResponse<>(HttpStatus.OK.value(), "register success", null);
    }

    @PostMapping("/test")
    public String addUser(@RequestBody String string) {

        return "string2";
    }



    @RequestMapping(value = "authenticate/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> authjwt(@RequestBody LoginUser loginUser) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
                loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success", new AuthToken(token, user.getUsername()));
    }





}
