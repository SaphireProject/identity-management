package idm.controller;

import idm.config.JwtTokenUtil;
import idm.data.User;
import idm.model.AuthUserResponse;
import idm.model.LoginUser;
import idm.model.RegisterUserResponse;
import idm.model.UserRegistrationDto;
import idm.repository.RepositoryUser;
import idm.service.AuthenticationService;
import idm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins="*")
public class RegistrationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public RegisterUserResponse addUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {

        authenticationService.register(userRegistrationDto);

        return new RegisterUserResponse(userRegistrationDto.getUsername(),
                userRegistrationDto.getPassword(),
                userRegistrationDto.getEmail());
    }

    @PostMapping("/test")
    public String addUser(@RequestBody String string) {

        return "string2";
    }



    @RequestMapping(value = "authenticate/generate-token", method = RequestMethod.POST)
    public AuthUserResponse auth(@RequestBody LoginUser loginUser) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(),
                loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return new AuthUserResponse(token, user.getUsername() );
    }





}
