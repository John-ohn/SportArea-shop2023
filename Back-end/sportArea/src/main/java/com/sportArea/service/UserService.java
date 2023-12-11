package com.sportArea.service;

import com.sportArea.entity.User;
import com.sportArea.entity.dto.UserRegistration;
import com.sportArea.entity.dto.UserDTOUpdate;
import com.sportArea.entity.dto.UserUpdateRequest;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserRegistration findById(Long userId);

    User findByIdInUser(Long userId);

    List<UserRegistration> findAll();

    Optional<User> findByEmail(String email);

    User findByEmailAndFirstName(String keyWord);

    void delete(Long userId);

    void deleteUsersBetweenIds(Long startId, Long endId);

    void save(UserRegistration user) throws MessagingException, IOException;

    User createUserFromUserDTO(UserRegistration userRegistration);

    UserRegistration createUserDTOFromUser(User user);

    UserDTOUpdate createUserForUpdate(Long userId, UserUpdateRequest fieldName);

     void validAndUpdateUser(@Valid UserDTOUpdate updates);

    void updateUserPassword(Long userId, String newPassword, String oldPassword);

}
