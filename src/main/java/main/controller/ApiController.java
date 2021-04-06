package main.controller;

import main.api.request.AddUserRequest;
import main.api.request.LogoutRequest;
import main.api.request.UpdateRequest;
import main.api.response.*;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ApiController {
    @Autowired
    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    @ResponseBody
    public LoginResponse login(@RequestParam String name, @RequestParam String password){
        return userService.login(name, password);
    }

    @PostMapping("/logout")
    public LogoutResponse logout(@RequestBody LogoutRequest userName){
        return userService.logout(userName);
    }

    @PostMapping("/update")
    public UpdateResponse update(@RequestBody UpdateRequest updateUser){
        return userService.update(updateUser);
    }

    @PostMapping("/addnew")
    public NewUserResponse addNew(@RequestBody AddUserRequest newUser){
        return userService.addNew(newUser);
    }

    @GetMapping("/getall")
    public UsersResponse getAll(@RequestParam String status){ return  userService.getAll(status);}

    @GetMapping("/getuser")
    public UserResponse getUser(@RequestParam String name){ return userService.getUser(name);}
}
