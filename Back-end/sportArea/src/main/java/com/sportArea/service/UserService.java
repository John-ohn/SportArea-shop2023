package com.sportArea.service;

import com.sportArea.entity.User;
import com.sportArea.entity.UserRegistration;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findById(Long userId);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    void delete(Long userId);

    void deleteUsersBetweenIds(Long startId, Long endId);

    User save(UserRegistration user);

}
