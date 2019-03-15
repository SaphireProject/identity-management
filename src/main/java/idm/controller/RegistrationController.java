package idm.controller;

import idm.config.JwtGenerator;
import idm.model.AuthUserResponse;
import idm.model.LoginUser;
import idm.model.UserRegistrationDto;
import idm.repository.RepositoryUser;
import idm.service.AuthenticationService;
import idm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")

public class RegistrationController {

    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtGenerator jwtGenerator;

    @RequestMapping(value = "registration", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        authenticationService.register(userRegistrationDto);

        return new ResponseEntity(OK);
    }

    @RequestMapping(value ="authenticate/generate-token", method = RequestMethod.POST)
    public AuthUserResponse login(@RequestBody LoginUser loginUser, HttpServletResponse response){
        authenticationService.authenticate(loginUser.getUsername(), loginUser.getPassword(), response);
        return new AuthUserResponse(response.getHeader(AUTHORIZATION).substring(BEARER_PREFIX.length()),
                loginUser.getUsername(),
                userService.findById(jwtGenerator.decodeNew(response.getHeader(AUTHORIZATION)).getUserData().getId()).getEmail());
    }

//написать для админа




}
