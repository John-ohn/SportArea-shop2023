package com.sportArea.service;

import com.sportArea.entity.User;
import com.sportArea.entity.dto.UserDTO;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO findById(Long userId);

    List<UserDTO> findAll();

    Optional<User> findByEmail(String email);

    User findByEmailAndFirstName(String keyWord);

    void delete(Long userId);

    void deleteUsersBetweenIds(Long startId, Long endId);

    void save(UserDTO user);

}