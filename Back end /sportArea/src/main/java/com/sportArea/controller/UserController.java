package com.sportArea.controller;

import com.sportArea.entity.User;
import com.sportArea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
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
    public User addUser(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable long userId) {
      return  userService.delete(userId);
    }


    @GetMapping("/welcome")
    @ResponseBody
    public String home() {

        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hellow get all user :  <a  href=\"http://onlinestore.eu-central-1.elasticbeanstalk.com/user/list\">All User Page</a>\"\n" + "</body>\n" + "</html>";
    }

}
