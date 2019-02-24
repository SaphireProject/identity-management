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
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<User> save(@RequestBody UserDto user){
        return new ApiResponse<>(/*HttpStatus.OK.value(), "User saved successfully.",*/ userService.save(user));
    }

    @GetMapping
    public ApiResponse<List<User>> listUser(){
        return new ApiResponse<>(/*HttpStatus.OK.value(), "User list fetched successfully.",*/userService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getOne(@PathVariable int id){
        return new ApiResponse<>(/*HttpStatus.OK.value(), "User fetched successfully.",*/userService.findById(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto> update(@RequestBody UserDto userDto) {
        return new ApiResponse<>(/*HttpStatus.OK.value(), "User updated successfully.",*/userService.update(userDto));
    }


    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return new ApiResponse<>(/*HttpStatus.OK.value(), "User fetched successfully.",*/ null);
    }




}
