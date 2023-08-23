package com.sportArea.controller;

import com.sportArea.entity.User;
import com.sportArea.entity.dto.UserDTO;
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
    public List<UserDTO> showAllUser() {

        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserFromId(@PathVariable long userId) {
        return userService.findById(userId);
    }

    @PostMapping("/registration")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserDTO user) {
        userService.save(user);
        return new ResponseEntity<>("Registration was Successful.", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
        userService.delete(userId);
        return new ResponseEntity<String>("User with userId: " + userId + " was deleted.", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/between")
    public ResponseEntity<String> deleteUsersBetweenIds(
            @RequestParam("startId") Long startId,
            @RequestParam("endId") Long endId) {
        userService.deleteUsersBetweenIds(startId, endId);
        return new ResponseEntity<String>(
                "Users between userIds: " + startId + " and " + endId + " was deleted.", HttpStatus.CREATED);
    }

    @GetMapping("/welcome")
    @ResponseBody
    public String home() {

        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hellow get all user :  <a  href=\"http://onlinestore.eu-central-1.elasticbeanstalk.com/user/list\">All User Page</a>\"\n" + "</body>\n" + "</html>";
    }

}
