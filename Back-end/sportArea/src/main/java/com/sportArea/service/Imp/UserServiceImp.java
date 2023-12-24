package com.sportArea.service.Imp;

import com.sportArea.dao.UserRepository;
import com.sportArea.entity.Customer;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.TypeRegistration;
import com.sportArea.entity.dto.UserRegistration;
import com.sportArea.entity.dto.UserDTOUpdate;
import com.sportArea.entity.dto.UserUpdateRequest;
import com.sportArea.exception.GeneralException;
import com.sportArea.service.EmailService;
import com.sportArea.service.PasswordGeneratorService;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@Validated
@Transactional
public class UserServiceImp implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImp.class);
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final PasswordGeneratorService passwordGeneratorService;

    @Autowired
    public UserServiceImp(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          @Qualifier("gmailSMTServiceImp") EmailService emailService,
                          PasswordGeneratorService passwordGeneratorService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.passwordGeneratorService = passwordGeneratorService;
    }

    @Override
    public UserRegistration findById(Long userId) {
        Optional<Customer> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Customer customer = userOptional.get();
            UserRegistration userRegistration = createUserDTOFromUser(customer);
            logger.info("From UserServiceImp method -findById- return User by id: {} ", userId);
            return userRegistration;
        } else {
            logger.warn("From UserServiceImp method -findById- send war message " +
                    "( User with userId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND);
            throw new GeneralException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Customer findByIdInUser(Long userId) {
        Optional<Customer> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            Customer customer = userOptional.get();
            logger.info("From UserServiceImp method -findById- return User by id: {} ", userId);
            return customer;
        } else {
            logger.warn("From UserServiceImp method -findById- send war message " +
                    "( User with userId: {} is not available. ({}))", userId, HttpStatus.NOT_FOUND);
            throw new GeneralException("User with userId: " + userId + " is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<UserRegistration> findAll() {
        List<Customer> customerList = userRepository.findAll();
        List<UserRegistration> userRegistrationList = customerList.stream().map(this::createUserDTOFromUser).toList();

        logger.info("From UserServiceImp method -findAll- return List of User .");
        return userRegistrationList;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        Optional<Customer> user = userRepository.findByEmail(email);
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
    public Customer findByEmailAndFirstName(String keyWord) {
        Optional<Customer> user = Optional.ofNullable(userRepository.findByEmailAndFirstName(keyWord));
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
    public void save(UserRegistration userRegistration) throws MessagingException, IOException {

        if (userRegistration != null) {
            if (userRegistration.getUserId() == null) {
                Optional<Customer> optionalUser = userRepository.findByEmail(userRegistration.getEmail());
                if (optionalUser.isPresent()) {
                    logger.warn("From UserServiceImp method -save- send war message " +
                            "( Email already exists. ({})))", HttpStatus.NO_CONTENT.name());
                    throw new GeneralException("Email already exists", HttpStatus.BAD_REQUEST);
                }
                String encodedPassword = passwordEncoder.encode(userRegistration.getPassword());
                Customer customer = createUserFromUserDTO(userRegistration);
                customer.setPassword(encodedPassword);
                customer.setRole(Role.ROLE_USER);
                customer.setStatus(Status.ACTIVE);
                customer.setTypeRegistration(TypeRegistration.FORM_REGISTRATION);
                userRepository.save(customer);

                logger.info("From UserServiceImp method -save- return new save User from Data Base.");

                emailService.sendHtmlEmailRegistration(customer.getEmail());

                logger.info("From UserServiceImp method -save- send Mail Registration .");
            } else {
                Optional<Customer> optionalUser = userRepository.findById(userRegistration.getUserId());
                if (optionalUser.isPresent()) {
                    logger.warn("From UserServiceImp method -save- send war message " +
                            "( User with this id already exists you can't save user twice. " +
                            "If you want update use another method ({})))", HttpStatus.BAD_REQUEST);

                    throw new GeneralException("User is not available or his is empty. ", HttpStatus.BAD_REQUEST);
                }
                logger.info("From UserServiceImp method -save- Made update User field in Data Base.");
            }
        } else {
            logger.warn("From UserServiceImp method -save- send war message " +
                    "( User is not available or his is empty. ({})))", HttpStatus.NOT_FOUND);

            throw new GeneralException("User is not available or his is empty. ", HttpStatus.NOT_FOUND);
        }
    }

    private void updateUser(UserDTOUpdate updates) {
        if (updates != null) {
            if (updates.getUserId() == null) {
                logger.warn("From UserServiceImp method -updateUser- send war message " +
                        "( User userId  is not available. ({}))", HttpStatus.NOT_FOUND);
                throw new GeneralException("User userId  is not available ", HttpStatus.NOT_FOUND);
            }
            Customer customer = createUserFromUpdate2(updates);

            userRepository.save(customer);

            logger.info("From UserServiceImp method -updateUser- Made update User field in Data Base.");

        } else {
            logger.warn("From UserServiceImp method -updateUser- send war message " +
                    "( User is not available or his is empty. ({})))", HttpStatus.NOT_FOUND);

            throw new GeneralException("User is not available or his is empty. ", HttpStatus.NOT_FOUND);
        }
    }

    //Make validation ond updates User fields.
    @Override
    public void validAndUpdateUser(@Valid UserDTOUpdate updates) {
        updateUser(updates);
    }

    @Override
    public UserDTOUpdate createUserForUpdate(Long userId, UserUpdateRequest fieldName) {
        Customer existingCustomer = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException("User is not available or his is empty. ", HttpStatus.NOT_FOUND));
        UserDTOUpdate user = createToUpdate2(existingCustomer);
        if (fieldName != null) {
            // Update fields.
            if (fieldName.getFirstName() != null) {
                user.setFirstName(fieldName.getFirstName());
            }
            // Update fields.
            if (fieldName.getLastName() != null) {
                user.setLastName(fieldName.getLastName());
            }
            // Update fields.
            if (fieldName.getEmail() != null) {
                user.setEmail(fieldName.getEmail());
            }
            // Update fields.
            if (fieldName.getPhoneNumber() != null) {
                user.setPhoneNumber(fieldName.getPhoneNumber());
            }

            return user;
        } else {
            logger.warn("From UserServiceImp method -updateAndValidUserFields- send war message " +
                    "(The password does not match. Write the correct valid password   ({}) )", HttpStatus.BAD_REQUEST);
            throw new GeneralException("User fields is empty.", HttpStatus.BAD_REQUEST);
        }
    }

    public void updateUserPassword(Long userId, String newPassword, String oldPassword) {
        Optional<Customer> user = userRepository.findById(userId);
        if (user.isPresent()) {

            boolean checkPassword = passwordEncoder.matches(oldPassword, user.get().getPassword());

            logger.info("From UserServiceImp method -updateUserPassword- check to password {} .",
                    checkPassword);

            if (checkPassword) {
                if (oldPassword.equals(newPassword)) {
                    logger.warn("From UserServiceImp method -updateUserPassword- send war message " +
                            "(The new password is the same as the old one. ({}) )", HttpStatus.BAD_REQUEST);
                    throw new GeneralException("The new password is the same as the old one.", HttpStatus.BAD_REQUEST);
                }

                userRepository.updateUserPassword(user.get().getUserId(), passwordEncoder.encode(newPassword));
            } else {
                logger.warn("From UserServiceImp method -updateUserPassword- send war message " +
                        "(The password does not match. Write the correct valid password   ({}) )", HttpStatus.NOT_FOUND.name());
                throw new GeneralException("The password does not match. Write the correct valid password.", HttpStatus.NOT_FOUND);
            }
        }


    }

    @Override
    public void forgotPassword(String email, int length) {
        if (email.isEmpty()) {
            logger.warn("From UserServiceImp method -forgotPassword- send war message " +
                    "( Email is  is empty. ({}))", HttpStatus.BAD_REQUEST);
            throw new GeneralException("Email is  is empty. ", HttpStatus.BAD_REQUEST);
        }
        Optional<Customer> user = userRepository.findByEmail(email);
        if (user.isPresent()) {

            String randomPassword = passwordGeneratorService.generatePassword(length);
            logger.info("From UserServiceImp method -forgotPassword- Generator new password to user {}, {}", email , randomPassword);

            userRepository.updateUserPassword(user.get().getUserId(),passwordEncoder.encode(randomPassword));
            logger.info("From UserServiceImp method -forgotPassword- save new password to user {}", email);

            emailService.sendHtmlEmailForgotPassword(email, randomPassword);
            logger.info("From UserServiceImp method -forgotPassword- send massage to email {}", email);
        } else {
            logger.warn("From UserServiceImp method -forgotPassword- send war message " +
                    "( User with this email is not available. ({}) ({})))", email, HttpStatus.NOT_FOUND);

            throw new GeneralException("User with this email is not available.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void delete(Long userId) {
        Optional<Customer> user = userRepository.findById(userId);
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
    public Customer createUserFromUserDTO(UserRegistration userRegistration) {
        if (userRegistration.getUserId() == null) {
            Customer customer = new Customer();
            customer.setFirstName(userRegistration.getFirstName());
            customer.setLastName(userRegistration.getLastName());
            customer.setEmail(userRegistration.getEmail());
            customer.setPhoneNumber(userRegistration.getPhoneNumber());
            customer.setPassword(userRegistration.getPassword());
            return customer;
        } else {
            Customer customer = new Customer();
            customer.setUserId(userRegistration.getUserId());
            customer.setFirstName(userRegistration.getFirstName());
            customer.setLastName(userRegistration.getLastName());
            customer.setEmail(userRegistration.getEmail());
            customer.setPhoneNumber(userRegistration.getPhoneNumber());
            customer.setPassword(userRegistration.getPassword());
            return customer;
        }

    }

    @Override
    public UserRegistration createUserDTOFromUser(Customer customer) {
        return UserRegistration
                .builder()
                .userId(customer.getUserId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .password(null)
                .role(customer.getRole())
                .status(customer.getStatus())
                .typeRegistration(customer.getTypeRegistration())
                .build();
    }

    public Customer createUserFromUpdate2(UserDTOUpdate userUpdate) {
        return Customer
                .builder()
                .userId(userUpdate.getUserId())
                .firstName(userUpdate.getFirstName())
                .lastName(userUpdate.getLastName())
                .email(userUpdate.getEmail())
                .phoneNumber(userUpdate.getPhoneNumber())
                .password(userUpdate.getPassword())
                .role(userUpdate.getRole())
                .status(userUpdate.getStatus())
                .typeRegistration(userUpdate.getTypeRegistration())
                .build();
    }

    public UserDTOUpdate createToUpdate2(Customer customerUpdate) {

        return UserDTOUpdate
                .builder()
                .userId(customerUpdate.getUserId())
                .firstName(customerUpdate.getFirstName())
                .lastName(customerUpdate.getLastName())
                .email(customerUpdate.getEmail())
                .phoneNumber(customerUpdate.getPhoneNumber())
                .password(customerUpdate.getPassword())
                .role(customerUpdate.getRole())
                .status(customerUpdate.getStatus())
                .typeRegistration(customerUpdate.getTypeRegistration())
                .build();

    }

    // Helper method to manually validate the UserDTOUpdate
    private boolean validateUserPassword(UserDTOUpdate user) {
        // Return false if there are no validation errors, true otherwise.
        return user.getPassword() == null || !user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.* ).{8,70}$");
    }

}
