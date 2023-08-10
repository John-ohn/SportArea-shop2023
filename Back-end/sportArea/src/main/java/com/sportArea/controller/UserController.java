package com.sportArea.controller;

import com.sportArea.entity.User;
import com.sportArea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public List<User> showAllUser() {

        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public User getUserFromId(@PathVariable long userId) {
        return userService.findById(userId);
    }

    @PostMapping("/registration")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        User saveUser = userService.save(user);
        return new ResponseEntity<User>(saveUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
        userService.delete(userId);
        return new ResponseEntity<String>( "User with userId: " + userId + " was deleted.", HttpStatus.CREATED);
    }

    @GetMapping("/welcome")
    @ResponseBody
    public String home() {

        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hellow get all user :  <a  href=\"http://onlinestore.eu-central-1.elasticbeanstalk.com/user/list\">All User Page</a>\"\n" + "</body>\n" + "</html>";
    }

}
