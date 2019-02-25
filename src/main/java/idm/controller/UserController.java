package idm.controller;


import idm.data.User;
import idm.model.ApiResponse;
import idm.model.UserDto;
import idm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    private UserService userService;

    /*
    @PostMapping
    public ApiResponse<User> save(@RequestBody UserDto user){
        return new ApiResponse<>( userService.save(user));
    }

    */

    //@GetMapping
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResponse<List<User>> listUser(){
        return new ApiResponse<>(userService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getOne(@PathVariable int id){
        return new ApiResponse<>(userService.findById(id));
    }


    /*
    @RequestMapping(path = "/me", method = RequestMethod.GET)
    public ApiResponse<UserDto> getPersonalPage(){


    }
    */

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public ApiResponse<UserDto> update(@PathVariable long id, @RequestBody UserDto userDto) {
        //userService.save(userDto);
        return new ApiResponse<>(userService.update(userDto,id));
    }


    //@DeleteMapping("/delete/{id}")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ApiResponse<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return new ApiResponse<>(null);
    }




}
