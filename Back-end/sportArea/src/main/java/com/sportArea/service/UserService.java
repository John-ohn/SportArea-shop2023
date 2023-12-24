package com.sportArea.service;

import com.sportArea.entity.Customer;
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

    Customer findByIdInUser(Long userId);

    List<UserRegistration> findAll();

    Optional<Customer> findByEmail(String email);

    Customer findByEmailAndFirstName(String keyWord);

    void delete(Long userId);

    void deleteUsersBetweenIds(Long startId, Long endId);

    void save(UserRegistration user) throws MessagingException, IOException;

    Customer createUserFromUserDTO(UserRegistration userRegistration);

    UserRegistration createUserDTOFromUser(Customer customer);

    UserDTOUpdate createUserForUpdate(Long userId, UserUpdateRequest fieldName);

     void validAndUpdateUser(@Valid UserDTOUpdate updates);

    void updateUserPassword(Long userId, String newPassword, String oldPassword);

    void forgotPassword(String email, int length);

}
