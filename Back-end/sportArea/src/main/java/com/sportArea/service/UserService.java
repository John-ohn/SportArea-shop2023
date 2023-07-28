package com.sportArea.service;

import com.sportArea.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findById(Long userId);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    String delete(Long userId);

    User save(User user);

}
