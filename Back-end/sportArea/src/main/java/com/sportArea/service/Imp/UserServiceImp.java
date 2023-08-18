package com.sportArea.service.Imp;

import com.sportArea.dao.UserRepository;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.User;
import com.sportArea.entity.UserRegistration;
import com.sportArea.exception.UserException;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {


    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("From UserServiceImp method -findById- return User by id: {} ", userId);
            return user;
        } else {
            logger.warn("From UserServiceImp method -findById- send war message " +
                    "( User with userId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND);
            throw new UserException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> userList = userRepository.findAll();
        logger.info("From UserServiceImp method -findAll- return List<User>.");
        return userList;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            logger.info("From UserServiceImp method -findByEmail- return User by email: {} ", email);
            return user;
        } else {
            logger.warn("From UserServiceImp method -findByEmail- send war message " +
                    "( User with email: {} is not available. ({}))", email, HttpStatus.NOT_FOUND);

            throw new UserException("User with email: " + email + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    public User save(UserRegistration userRegistration) {

        if (userRegistration != null) {
            Optional<User> optionalUser = userRepository.findByEmail(userRegistration.getEmail());
            if (optionalUser.isPresent()) {
                throw new UserException("Email already exists", HttpStatus.BAD_REQUEST);
            }
            String encodedPassword = passwordEncoder.encode(userRegistration.getPassword());
            User user = createUser(userRegistration);
            user.setPassword(encodedPassword);
            user.setRole(Role.USER);
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
            logger.info("From UserServiceImp method -save- return new save User from Data Base.");
            return user;
        } else {
            logger.warn("From UserServiceImp method -save- send war message " +
                    "( User is not available or his is empty. ({})))", HttpStatus.NO_CONTENT);

            throw new UserException("User is not available or his is empty. ", HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public void delete(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            logger.info("From UserServiceImp method -delete- return message (User with userId: {} was deleted.).", userId);
        } else {
            logger.warn("From UserServiceImp method -delete- send war message " +
                    "(User with userId: {} is not available. ({}) )", userId, HttpStatus.NOT_FOUND);
            throw new UserException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteUsersBetweenIds(Long startId, Long endId) {

        if (startId >= 0 && endId > startId + 1) {
            userRepository.deleteBetweenIds(startId, endId);
            logger.info(
                    "From UserServiceImp method -deleteUsersBetweenIds- return message (Users between userIds: " +
                            "{} and {} was deleted.).", startId, endId);
        } else {
            logger.warn("From UserServiceImp method -deleteUsersBetweenIds- send war message " +
                    "(Users with userIds: {} and {} is not available. {}", startId, endId, HttpStatus.NOT_FOUND);
            throw new UserException("Users with userIds: " + startId + "and " + endId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    public User createUser(UserRegistration userRegistration){
        User user= new User();
        user.setFirstName(userRegistration.getFirstName());
        user.setLastName(userRegistration.getLastName());
        user.setEmail(userRegistration.getEmail());
        user.setPhoneNumber(userRegistration.getPhoneNumber());
        user.setPassword(userRegistration.getPassword());
        return  user;
    }
}
