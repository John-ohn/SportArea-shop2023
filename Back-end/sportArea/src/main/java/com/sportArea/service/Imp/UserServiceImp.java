package com.sportArea.service.Imp;

import com.sportArea.dao.UserRepository;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.User;
import com.sportArea.exception.UserException;
import com.sportArea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user;
        } else {
            throw new UserException("User with email: " + email + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    public User save(User user) {

        if (user != null) {
            Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
            if (optionalUser.isPresent()) {
                throw new UserException("Email already exists", HttpStatus.BAD_REQUEST);
            }
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);

            return user;
        } else {
            throw new UserException("User is not available or his is empty. ", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public String delete(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return "User with userId: " + userId + " was deleted.";
        } else {
            throw new UserException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }
}
