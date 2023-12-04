package com.sportArea.service.Imp;

import com.sportArea.dao.UserRepository;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.TypeRegistration;
import com.sportArea.entity.User;
import com.sportArea.entity.dto.UserDTO;
import com.sportArea.entity.dto.UserDTOUpdate;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
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
    public UserDTO findById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            UserDTO userDTO = createUserDTOFromUser(user);
            logger.info("From UserServiceImp method -findById- return User by id: {} ", userId);
            return userDTO;
        } else {
            logger.warn("From UserServiceImp method -findById- send war message " +
                    "( User with userId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND);
            throw new GeneralException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User findByIdInUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("From UserServiceImp method -findById- return User by id: {} ", userId);
            return user;
        } else {
            logger.warn("From UserServiceImp method -findById- send war message " +
                    "( User with userId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND);
            throw new GeneralException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = userList.stream().map(this::createUserDTOFromUser).toList();

        logger.info("From UserServiceImp method -findAll- return List of User .");
        return userDTOList;
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

            throw new GeneralException("User with email: " + email + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User findByEmailAndFirstName(String keyWord) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmailAndFirstName(keyWord));
        if (user.isPresent()) {
            logger.info("From UserServiceImp method -findByEmailAndFirstName- return User by keyWord: {} ", keyWord);
            return user.get();
        } else {
            logger.warn("From UserServiceImp method -findByEmail- send war message " +
                    "( User with keyWord: {} is not available. ({}))", keyWord, HttpStatus.NOT_FOUND);

            throw new GeneralException("User with keyWord: " + keyWord + " is not available.", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public void save(UserDTO userDTO) {

        if (userDTO != null) {
            if (userDTO.getUserId() == null) {
                Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
                if (optionalUser.isPresent()) {
                    logger.warn("From UserServiceImp method -save- send war message " +
                            "( Email already exists. ({})))", HttpStatus.NO_CONTENT.name());
                    throw new GeneralException("Email already exists", HttpStatus.BAD_REQUEST);
                }
                String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
                User user = createUserFromUserDTO(userDTO);
                user.setPassword(encodedPassword);
                user.setRole(Role.ROLE_USER);
                user.setStatus(Status.ACTIVE);
                user.setTypeRegistration(TypeRegistration.FORM_REGISTRATION);
                userRepository.save(user);

                logger.info("From UserServiceImp method -save- return new save User from Data Base.");
            } else {

                User user = createUserFromUserDTO(userDTO);
                user.setPassword(userDTO.getPassword());
                user.setRole(userDTO.getRole());
                user.setStatus(userDTO.getStatus());
                user.setTypeRegistration(userDTO.getTypeRegistration());
                userRepository.save(user);

                logger.info("From UserServiceImp method -save- Made update User field in Data Base.");
            }
        } else {
            logger.warn("From UserServiceImp method -save- send war message " +
                    "( User is not available or his is empty. ({})))", HttpStatus.NOT_FOUND);

            throw new GeneralException("User is not available or his is empty. ", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void updateUserFields(Long userId, String fieldName, String updates) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User is not available or his is empty. ", HttpStatus.NOT_FOUND));

        // Update fields based on the provided map
        if (fieldName.equals("firstName")) {
            userRepository.updateUserFirstName(existingUser.getUserId(), updates);
        }
        if (fieldName.equals("lastName")) {
            userRepository.updateUserLastName(existingUser.getUserId(), updates);
        }
        if (fieldName.equals("email")) {
            userRepository.updateUserEmail(existingUser.getUserId(), updates);
        }
        if (fieldName.equals("phoneNumber")) {
            userRepository.updateUserPhoneNumber(existingUser.getUserId(), updates);
        }
        if (fieldName.equals("password")) {
            String encodedPassword = passwordEncoder.encode(updates);
            userRepository.updateUserPassword(existingUser.getUserId(), encodedPassword);
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
                    "(User with userId: {} is not available. ({}) )", userId, HttpStatus.NOT_FOUND.name());
            throw new GeneralException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
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
                    "(Users with userIds: {} and {} is not available. {}", startId, endId, HttpStatus.NOT_FOUND.name());
            throw new GeneralException("Users with userIds: " + startId + "and " + endId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User createUserFromUserDTO(UserDTO userDTO) {
        if (userDTO.getUserId() == null) {
            User user = new User();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setPassword(userDTO.getPassword());
            return user;
        } else {
            User user = new User();
            user.setUserId(userDTO.getUserId());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setPassword(userDTO.getPassword());
            return user;
        }

    }

    @Override
    public UserDTO createUserDTOFromUser(User user) {
        return UserDTO
                .builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(null)
                .role(user.getRole())
                .status(user.getStatus())
                .typeRegistration(TypeRegistration.FORM_REGISTRATION)
                .build();
    }

    @Override
    public UserDTO createToUpdate(UserDTOUpdate userUpdate) {
        if (ObjectUtils.isEmpty(userUpdate.getPassword())) {
            Optional<User> existingUser = userRepository.findById(userUpdate.getUserId());
            if (existingUser.isPresent()) {
                UserDTO user = UserDTO
                        .builder()
                        .userId(existingUser.get().getUserId())
                        .firstName(userUpdate.getFirstName())
                        .lastName(userUpdate.getLastName())
                        .email(userUpdate.getEmail())
                        .phoneNumber(userUpdate.getPhoneNumber())
                        .password(existingUser.get().getPassword())
                        .role(existingUser.get().getRole())
                        .status(existingUser.get().getStatus())
                        .typeRegistration(existingUser.get().getTypeRegistration())
                        .build();
                return user;
            } else {
                logger.warn("From UserServiceImp method -createToUpdate- send war message " +
                        "( User with userId: {} is not available. ({}))", userUpdate.getUserId(), HttpStatus.NOT_FOUND);
                throw new GeneralException("User with userId: " + userUpdate.getUserId() + " is not available.", HttpStatus.NOT_FOUND);
            }

        } else {
            if (!validateUserPassword(userUpdate)) {
                Optional<User> existingUser = userRepository.findById(userUpdate.getUserId());
                if (existingUser.isPresent()) {
                    String encodedPassword = passwordEncoder.encode(userUpdate.getPassword());
                    UserDTO user = UserDTO
                            .builder()
                            .userId(existingUser.get().getUserId())
                            .firstName(userUpdate.getFirstName())
                            .lastName(userUpdate.getLastName())
                            .email(userUpdate.getEmail())
                            .phoneNumber(userUpdate.getPhoneNumber())
                            .password(encodedPassword)
                            .role(existingUser.get().getRole())
                            .status(existingUser.get().getStatus())
                            .typeRegistration(existingUser.get().getTypeRegistration())
                            .build();
                    return user;
                } else {
                    logger.warn("From UserServiceImp method -createToUpdate- send war message " +
                            "( User with userId: {} is not available. ({}))", userUpdate.getUserId(), HttpStatus.NOT_FOUND);
                    throw new GeneralException("User with userId: " + userUpdate.getUserId() + " is not available.", HttpStatus.NOT_FOUND);
                }
            } else {
                throw new GeneralException("Password must contain at least one lowercase letter, one uppercase letter, one digit, no space", HttpStatus.NOT_FOUND);
            }
        }

    }

    // Helper method to manually validate the UserDTOUpdate
    private boolean validateUserPassword(UserDTOUpdate user) {

        // Return false if there are no validation errors, true otherwise.
        return user.getPassword() == null || !user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.* ).{8,70}$");
    }

}
