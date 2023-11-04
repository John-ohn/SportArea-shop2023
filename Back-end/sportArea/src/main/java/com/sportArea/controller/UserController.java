package com.sportArea.controller;

import com.sportArea.entity.dto.UserDTO;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> showAllUser() {
        logger.info("From UserController  controller -showAllUser- /users. Return List of Users.");
        return userService.findAll();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable long userId) {
        UserDTO user = userService.findById(userId);
        logger.info("From UserController method -getUserById- /users/{userId}. Return User");
        return user;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> addUser(@Valid @RequestBody UserDTO user) {
        userService.save(user);
        logger.info("From UserController controller -addUser-  /users/registration . Save new user.");
        return new ResponseEntity<>("Registration was Successful.", HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
        userService.delete(userId);
        logger.info("From UserController controller -deleteUser- /users/{userId}. Delete User By userId: {}", userId);
        return new ResponseEntity<>("User with userId: " + userId + " was deleted.", HttpStatus.CREATED);
    }

    @DeleteMapping("/between")
    public ResponseEntity<String> deleteUsersBetweenIds(
            @RequestParam("startId") Long startId,
            @RequestParam("endId") Long endId) {

        userService.deleteUsersBetweenIds(startId, endId);
        logger.info("From UserController controller -deleteUsersBetweenIds- /users/between. Delete Users {} - {}",
                startId, endId);
        return new ResponseEntity<String>(
                "Users between userIds: " + startId + " and " + endId + " was deleted.", HttpStatus.CREATED);
    }

    @GetMapping("/welcome")
    @ResponseBody
    public String home() {

        return "<html>\n" + "<header><title>Welcome</title></header>\n" +
                "<body>\n" + "Hellow get all user :  <a  href=\"http://onlinestore.eu-central-1.elasticbeanstalk.com/user/list\">All User Page</a>\"\n" + "</body>\n" + "</html>";
    }


    @GetMapping("/testAuth")
    public ResponseEntity<?> testAuth() {
        UserDTO userDTO = userService.findById(1L);

        logger.info("From UserController controller -testAuth- /users/testAuth. Test login and work jwt token  return user with id 1 ");
        return ResponseEntity.ok(userDTO);
    }
}
