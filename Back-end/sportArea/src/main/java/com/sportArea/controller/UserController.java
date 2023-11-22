package com.sportArea.controller;


import com.sportArea.entity.Status;
import com.sportArea.entity.dto.RegistrationStatus;
import com.sportArea.entity.dto.UserDTO;
import com.sportArea.entity.dto.UserDTOUpdate;
import com.sportArea.entity.dto.userFeilds.*;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    private RestTemplate restTemplate;

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
    public ResponseEntity<RegistrationStatus> addUser(@Valid @RequestBody UserDTO user) {
        userService.save(user);
        logger.info("From UserController controller -addUser-  /users/registration . Save new user.");
        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Registration was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RegistrationStatus> updateUser(@Valid @RequestBody UserDTOUpdate user) {
        UserDTO existingUser = userService.createToUpdate(user);

        userService.save(existingUser);
        logger.info("From UserController controller -updateUser-  /api/v1/users . Update user.");
        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }
    @PatchMapping("/{userId}/first-name")
    public ResponseEntity<RegistrationStatus> updateUserFirstName(@Valid @RequestBody UserUpdateFirstName firstName,
                                                                  @PathVariable ("userId") Long userId){

        userService.updateUserFields(userId,"firstName", firstName.getFirstName());
        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}/last-name")
    public ResponseEntity<RegistrationStatus> updateUserLastName(@Valid @RequestBody UserUpdateLastName lastName,
                                                                  @PathVariable ("userId") Long userId){

        userService.updateUserFields(userId,"lastName", lastName.getLastName());
        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}/email")
    public ResponseEntity<RegistrationStatus> updateUserEmail(@Valid @RequestBody UserUpdateEmail email,
                                                                 @PathVariable ("userId") Long userId){

        userService.updateUserFields(userId,"email", email.getEmail());
        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}/phone-number")
    public ResponseEntity<RegistrationStatus> updateUserPhoneNumber(@Valid @RequestBody UserUpdatePhoneNumber phoneNumber,
                                                              @PathVariable ("userId") Long userId){

        userService.updateUserFields(userId,"email", phoneNumber.getPhoneNumber());
        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<RegistrationStatus> updateUserPassword(@Valid @RequestBody UserUpdatePassword password,
                                                                    @PathVariable ("userId") Long userId){

        userService.updateUserFields(userId,"password", password.getPassword());
        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<RegistrationStatus> updateUserStatus(@RequestParam("status") Status status,
                                                                 @PathVariable ("userId") Long userId){

        userService.updateUserFields(userId,"status", status.toString());
        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
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
