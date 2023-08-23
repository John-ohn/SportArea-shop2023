package com.sportArea.service;

import com.sportArea.entity.User;
import com.sportArea.entity.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO findById(Long userId);

    List<UserDTO> findAll();

    Optional<User> findByEmail(String email);

    void delete(Long userId);

    void deleteUsersBetweenIds(Long startId, Long endId);

    void save(UserDTO user);

}
