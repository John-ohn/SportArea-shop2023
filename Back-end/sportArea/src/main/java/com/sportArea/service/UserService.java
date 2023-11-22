package com.sportArea.service;

import com.sportArea.entity.User;
import com.sportArea.entity.dto.UserDTO;
import com.sportArea.entity.dto.UserDTOUpdate;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    UserDTO findById(Long userId);

    User findByIdInUser(Long userId);

    List<UserDTO> findAll();

    Optional<User> findByEmail(String email);

    User findByEmailAndFirstName(String keyWord);

    void delete(Long userId);

    void deleteUsersBetweenIds(Long startId, Long endId);

    void save(UserDTO user);

    User createUserFromUserDTO(UserDTO userDTO);

    UserDTO createUserDTOFromUser(User user);

    UserDTO createToUpdate(UserDTOUpdate userUpdate);

    void updateUserFields(Long userId,String fieldName, String updates);

}
