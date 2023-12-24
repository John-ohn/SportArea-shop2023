package com.sportArea.service.Imp;

import com.sportArea.dao.UserRepository;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.TypeRegistration;
import com.sportArea.entity.Customer;
import com.sportArea.entity.dto.GoogleUserDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.security.JwtTokenProvider;
import com.sportArea.service.GoogleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GoogleUserServiceImp implements GoogleUserService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(GoogleUserServiceImp.class);

    @Override
    public void saveUserGoogle(GoogleUserDTO googleUser) {

        if (googleUser != null) {
            Optional<Customer> optionalUser = userRepository.findByEmail(googleUser.getEmail());
            if (optionalUser.isPresent()) {
                logger.info("From GoogleUserServiceImp method -saveUserGoogle- send war message " +
                        "( Email already exists. ({})))", HttpStatus.NOT_FOUND.name());
            } else {

                Customer customer = createUserFromGoogleUser(googleUser);
                userRepository.save(customer);

                logger.info("From UserServiceImp method -save- return new save User from Data Base.");
            }

        } else {
            logger.info("From GoogleUserServiceImp method -saveUserGoogle- send war message " +
                    "( User is not available or his is empty. ({})))", HttpStatus.NOT_FOUND);

            throw new GeneralException("User is not available or his is empty. ", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Map<Object, Object> getJwtTokenFromGoogle(OAuth2User oauth2User) {

        GoogleUserDTO googleUse = createGoogleUserFromOAuth2User(oauth2User);

        saveUserGoogle(googleUse);
        Customer customer = userRepository.findByEmail(googleUse.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("User doesn't exists"));
        String token = jwtTokenProvider.createToken(googleUse.getEmail(), googleUse.getRole().name(), customer.getUserId());

        Map<Object, Object> response = new HashMap<>();
        response.put("email", googleUse.getEmail());
        response.put("token", token);
        response.put("userId", customer.getUserId());

        return response;
    }

    public Customer createUserFromGoogleUser(GoogleUserDTO googleUser) {
        Customer customer = new Customer();
        customer.setFirstName(googleUser.getFirstName());
        customer.setLastName(googleUser.getLastName());
        customer.setEmail(googleUser.getEmail());
        customer.setPassword(googleUser.getPassword());
        customer.setRole(googleUser.getRole());
        customer.setStatus(googleUser.getStatus());
        customer.setTypeRegistration(googleUser.getTypeRegistration());
        return customer;
    }

    public GoogleUserDTO createGoogleUserFromOAuth2User(OAuth2User oAuth2User) {

        Map<String, String> mapFirstLastName = getFirstLastName(oAuth2User);
        String passwordStub = oAuth2User.getName() + oAuth2User.getAuthorities().toString();

        GoogleUserDTO googleUse = new GoogleUserDTO();
        googleUse.setEmail(oAuth2User.getAttribute("email"));
        googleUse.setPassword(passwordEncoder.encode(passwordStub));
        googleUse.setFirstName(mapFirstLastName.get("firstName"));
        googleUse.setLastName(mapFirstLastName.get("lastName"));
        googleUse.setRole(Role.ROLE_USER);
        googleUse.setStatus(Status.ACTIVE);
        googleUse.setTypeRegistration(TypeRegistration.GOOGLE);

        logger.info("Try get google user info (OAuth2User) name: " + googleUse.getFirstName()
                + " Last name: " + googleUse.getLastName()
                + "email: " + googleUse.getEmail()
                + "; roles: " +
                oAuth2User.getAuthorities().toString() + "   .." + oAuth2User.getName());

        return googleUse;
    }

    public Map<String, String> getFirstLastName(OAuth2User oAuth2User) {
        String[] firstLastName = Objects.requireNonNull(oAuth2User.getAttribute("name"))
                .toString()
                .split(" ");

        Map<String, String> googleAccount = new HashMap<>();
        googleAccount.put("firstName", firstLastName[0]);

        if (firstLastName.length <= 1) {
            return googleAccount;
        } else {
            googleAccount.put("lastName", firstLastName[1]);
            return googleAccount;
        }
    }
}
