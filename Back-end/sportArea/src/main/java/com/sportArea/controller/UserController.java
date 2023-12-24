package com.sportArea.controller;


import com.sportArea.dao.UserRepository;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.TypeRegistration;
import com.sportArea.entity.dto.RegistrationStatus;
import com.sportArea.entity.dto.UserRegistration;
import com.sportArea.entity.dto.UserDTOUpdate;
import com.sportArea.entity.dto.UserUpdateRequest;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.entity.dto.userFeilds.*;
import com.sportArea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    private final GeneralLogg generalLogg;

    private final UserService userService;

    private RestTemplate restTemplate;

    private UserRepository userRepository;


    @Autowired
    public UserController(UserService userService, GeneralLogg generalLogg,UserRepository userRepository) {
        this.userService = userService;
        this.generalLogg = generalLogg;
        this.userRepository=userRepository;
    }

    @GetMapping
    public List<UserRegistration> showAllUser() {

        List<UserRegistration> userList = userService.findAll();

        generalLogg.getLoggerControllerInfo("UserController",
                "showAllUser",
                "/users",
                "List of all Users");

        return userList;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRegistration> getUserById(@PathVariable long userId) {
        UserRegistration user = userService.findById(userId);

        generalLogg.getLoggerControllerInfo("UserController",
                "getUserById",
                "/users/{userId}",
                "User with userId: " + userId);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/registration")
    public ResponseEntity<RegistrationStatus> addUser(@Valid @RequestBody UserRegistration user) throws MessagingException, IOException {
        userService.save(user);

        generalLogg.getLoggerControllerInfo("UserController",
                "addUser",
                "/registration",
                "message (Registration was Successful.) and Save new User with user email: " + user.getEmail());

        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Registration was Successful.");

        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }


    @PatchMapping("/{userId}")
    public ResponseEntity<RegistrationStatus> updateUser(@PathVariable("userId") Long userId,
                                                         @RequestBody UserUpdateRequest user) {

        UserDTOUpdate userDTOUpdate = userService.createUserForUpdate(userId, user);

        userService.validAndUpdateUser(userDTOUpdate);

        generalLogg.getLoggerControllerInfo("UserController",
                "updateUser",
                "/users",
                "message (Update was Successful.) and update User with user email: " + user.getEmail());

        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");

        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

    @PostMapping("/forgot/password")
    public ResponseEntity<String> forgotPassword(@RequestParam("email")String email){

        userService.forgotPassword(email,12);

        generalLogg.getLoggerControllerInfo("UserController",
                "forgotPassword",
                "/forgot/password",
                "message (We have sent a new password to your email address.) and update User password");

        return new ResponseEntity<>("We have sent a new password to your email address.", HttpStatus.OK);
    }

    @PatchMapping("/{userId}/first-name")
    public ResponseEntity<RegistrationStatus> updateUserFirstName(@Valid @RequestBody UserUpdateFirstName firstName,
                                                                  @PathVariable("userId") Long userId) {

        userRepository.updateUserFirstName(userId, firstName.getFirstName());

        generalLogg.getLoggerControllerInfo("UserController",
                "updateUserFirstName",
                "/{userId}/first-name",
                "message (Update was Successful.) and update User first name to - " + firstName.getFirstName() + " -. with userId: " + userId);

        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

//    @PatchMapping("/{userId}/last-name")
//    public ResponseEntity<RegistrationStatus> updateUserLastName(@Valid @RequestBody UserUpdateLastName lastName,
//                                                                 @PathVariable("userId") Long userId) {
//
////        userService.updateUserFields(userId, "lastName", lastName.getLastName());
//
//        generalLogg.getLoggerControllerInfo("UserController",
//                "updateUserLastName",
//                "/{userId}/last-name",
//                "message (Update was Successful.) and update User last name to - " + lastName.getLastName() + " -. with userId: " + userId);
//
//        RegistrationStatus registrationStatus = new RegistrationStatus(
//                HttpStatus.CREATED.value(),
//                "Update was Successful.");
//        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
//    }

//    @PatchMapping("/{userId}/email")
//    public ResponseEntity<RegistrationStatus> updateUserEmail(@Valid @RequestBody UserUpdateEmail email,
//                                                              @PathVariable("userId") Long userId) {
//
////        userService.updateUserFields(userId, "email", email.getEmail());
//
//        generalLogg.getLoggerControllerInfo("UserController",
//                "updateUserEmail",
//                "/{userId}/email",
//                "message (Update was Successful.) and update User email to - " + email.getEmail() + " -. with userId: " + userId);
//
//        RegistrationStatus registrationStatus = new RegistrationStatus(
//                HttpStatus.CREATED.value(),
//                "Update was Successful.");
//
//        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
//    }

//    @PatchMapping("/{userId}/phone-number")
//    public ResponseEntity<RegistrationStatus> updateUserPhoneNumber(@Valid @RequestBody UserUpdatePhoneNumber phoneNumber,
//                                                                    @PathVariable("userId") Long userId) {
//
////        userService.updateUserFields(userId, "phoneNumber", phoneNumber.getPhoneNumber());
//
//        generalLogg.getLoggerControllerInfo("UserController",
//                "updateUserPhoneNumber",
//                "/{userId}/phone-number",
//                "message (Update was Successful.) and update User phone number to - " + phoneNumber.getPhoneNumber() + " -. with userId: " + userId);
//
//        RegistrationStatus registrationStatus = new RegistrationStatus(
//                HttpStatus.CREATED.value(),
//                "Update was Successful.");
//        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
//    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<RegistrationStatus> updateUserPassword(@Valid @RequestBody UserUpdatePassword password,
                                                                 @PathVariable("userId") Long userId) {

        userService.updateUserPassword(userId, password.getNewPassword(), password.getOldPassword());

        generalLogg.getLoggerControllerInfo("UserController",
                "updateUserPassword",
                "/{userId}/password",
                "message (Update was Successful.) and update User password with userId: " + userId);

        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");
        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<RegistrationStatus> updateUserStatus(@RequestParam("status") Status status,
                                                               @PathVariable("userId") Long userId) {

//        userService.updateUserFields(userId, "status", status.toString());

        generalLogg.getLoggerControllerInfo("UserController",
                "updateUserStatus",
                "/{userId}/status",
                "message (Update was Successful.) and update User status to - " + status.name() + " -. with userId: " + userId);

        RegistrationStatus registrationStatus = new RegistrationStatus(
                HttpStatus.CREATED.value(),
                "Update was Successful.");

        return new ResponseEntity<>(registrationStatus, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable long userId) {
        userService.delete(userId);

        generalLogg.getLoggerControllerInfo("UserController",
                "deleteUser",
                "/{userId}",
                "message (User was deleted.) and Delete User By userId: " + userId);

        return new ResponseEntity<>("User with userId: " + userId + " was deleted.", HttpStatus.CREATED);
    }

    @DeleteMapping("/between")
    public ResponseEntity<String> deleteUsersBetweenIds(
            @RequestParam("startId") Long startId,
            @RequestParam("endId") Long endId) {

        userService.deleteUsersBetweenIds(startId, endId);

        generalLogg.getLoggerControllerInfo("UserController",
                "deleteUsersBetweenIds",
                "/between",
                "message (Users was deleted.) and  Delete Users between: " + startId + " - " + endId);

        return new ResponseEntity<String>(
                "Users between userIds: " + startId + " and " + endId + " was deleted.", HttpStatus.CREATED);
    }

    @GetMapping("/testAuth")
    public ResponseEntity<?> testAuth() {
        UserRegistration userRegistration = UserRegistration.builder()
                .typeRegistration(TypeRegistration.FORM_REGISTRATION)
                .email("test.token.@work.com")
                .firstName("TEST_TOKEN")
                .lastName("TOKEN_WORK")
                .password("YouCanWrite1")
                .phoneNumber("380123456789")
                .role(Role.ROLE_GUEST)
                .userId(1L)
                .status(Status.ACTIVE)
                .build();

        generalLogg.getLoggerControllerInfo("UserController",
                "testAuth",
                "/testAuth",
                "Test login and work jwt token return TEST_TOKEN User.");

        return ResponseEntity.ok(userRegistration);
    }
}
