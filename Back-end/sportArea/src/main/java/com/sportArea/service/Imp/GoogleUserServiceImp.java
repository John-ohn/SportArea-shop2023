package com.sportArea.service.Imp;

import com.sportArea.dao.UserRepository;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.TypeRegistration;
import com.sportArea.entity.User;
import com.sportArea.entity.dto.GoogleUserDTO;
import com.sportArea.exception.UserException;
import com.sportArea.security.JwtTokenProvider;
import com.sportArea.service.GoogleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            Optional<User> optionalUser = userRepository.findByEmail(googleUser.getEmail());
            if (optionalUser.isPresent()) {
                logger.info("From GoogleUserServiceImp method -saveUserGoogle- send war message " +
                        "( Email already exists. ({})))", HttpStatus.NOT_FOUND.name());
            } else {

                User user = createUserFromGoogleUser(googleUser);
                userRepository.save(user);

                logger.info("From UserServiceImp method -save- return new save User from Data Base.");
            }

        } else {
            logger.info("From GoogleUserServiceImp method -saveUserGoogle- send war message " +
                    "( User is not available or his is empty. ({})))", HttpStatus.NOT_FOUND);

            throw new UserException("User is not available or his is empty. ", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Map<Object, Object> getJwtTokenFromGoogle(OAuth2User oauth2User) {

        GoogleUserDTO googleUse = createGoogleUserFromOAuth2User(oauth2User);

        saveUserGoogle(googleUse);

        String token = jwtTokenProvider.createToken(googleUse.getEmail(), googleUse.getRole().name());

        Map<Object, Object> response = new HashMap<>();
        response.put("email", googleUse.getEmail());
        response.put("token", token);

        return response;
    }

    public User createUserFromGoogleUser(GoogleUserDTO googleUser) {
        User user = new User();
        user.setFirstName(googleUser.getFirstName());
        user.setLastName(googleUser.getLastName());
        user.setEmail(googleUser.getEmail());
        user.setPassword(googleUser.getPassword());
        user.setRole(googleUser.getRole());
        user.setStatus(googleUser.getStatus());
        user.setTypeRegistration(googleUser.getTypeRegistration());
        return user;
    }

    public GoogleUserDTO createGoogleUserFromOAuth2User(OAuth2User oAuth2User) {

        Map<String, String> mapFirstLastName = getFirstLastName(oAuth2User);
        String passwordStub = oAuth2User.getName() + oAuth2User.getAuthorities().toString();

        GoogleUserDTO googleUse = new GoogleUserDTO();
        googleUse.setEmail(oAuth2User.getAttribute("email"));
        googleUse.setPassword(passwordEncoder.encode(passwordStub));
        googleUse.setFirstName(mapFirstLastName.get("FirstName"));
        googleUse.setLastName(mapFirstLastName.get("LastName"));
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

        return Map.of("FirstName", firstLastName[0],
                "LastName", firstLastName[1]);
    }
}
