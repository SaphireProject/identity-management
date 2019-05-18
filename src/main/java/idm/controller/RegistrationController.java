package idm.controller;

import idm.config.JwtGenerator;
import idm.model.AuthUserResponse;
import idm.model.LoginUser;
import idm.model.UserRegistrationDto;
import idm.repository.RepositoryUser;
import idm.service.AuthenticationService;
import idm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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
    public AuthUserResponse addUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto,
                                  HttpServletResponse response) {
        authenticationService.register(userRegistrationDto);
        authenticationService.authenticate(userRegistrationDto.getUsername(),
                userRegistrationDto.getPassword(),
                response);

        return new AuthUserResponse(
                response.getHeader(AUTHORIZATION).substring(BEARER_PREFIX.length()),
                userRegistrationDto.getUsername(),
                userService.findById(jwtGenerator.decodeNew(response.getHeader(AUTHORIZATION)).getUserData().getId()).getEmail(),
                userService.findOne(userRegistrationDto.getUsername()).getId()
        );
    }

    @RequestMapping(value ="authenticate/generate-token", method = RequestMethod.POST)
    public AuthUserResponse login(@RequestBody LoginUser loginUser, HttpServletResponse response){
        authenticationService.authenticate(loginUser.getUsername(), loginUser.getPassword(), response);
        return new AuthUserResponse(
                response.getHeader(AUTHORIZATION).substring(BEARER_PREFIX.length()),
                loginUser.getUsername(),
                userService.findById(jwtGenerator.decodeNew(response.getHeader(AUTHORIZATION)).getUserData().getId()).getEmail(),
                userService.findOne(loginUser.getUsername()).getId()
        );
    }

//написать для админа




}
