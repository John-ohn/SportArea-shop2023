package com.sportArea.service;

import com.sportArea.entity.User;

import java.util.List;

public interface UserService {

    User findById(Long userId);

    List<User> findAll();

    String delete(Long userId);

    User save(User user);

}
