package com.sportArea.service.Imp;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.sportArea.dao.CustomerRepository;
import com.sportArea.entity.Role;
import com.sportArea.entity.Status;
import com.sportArea.entity.TypeRegistration;
import com.sportArea.entity.Customer;
import com.sportArea.entity.dto.GoogleUserDTO;
import com.sportArea.entity.dto.restJWT.AuthenticationGoogleRequestDto;
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


//    @Value("${google.clientID}")
//    private String googleClientId="535501168701-jn3j530ctea52lth65lndv7jlov632bi.apps.googleusercontent.com";

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(GoogleUserServiceImp.class);

    @Override
    public void saveUserGoogle(GoogleUserDTO googleUser) {

        if (googleUser != null) {
            Optional<Customer> optionalUser = customerRepository.findByEmail(googleUser.getEmail());
            if (optionalUser.isPresent()) {
                logger.info("From GoogleUserServiceImp method -saveUserGoogle- send war message " +
                        "( Email already exists. ({})))", HttpStatus.NOT_FOUND.name());
            } else {

                Customer customer = createUserFromGoogleUser(googleUser);
                customerRepository.save(customer);

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
        Customer customer = customerRepository.findByEmail(googleUse.getEmail()).orElseThrow(
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




    @Override
    public Customer loginGoogle(AuthenticationGoogleRequestDto requestDto) {
       try {
           String googleClientId = "535501168701-jn3j530ctea52lth65lndv7jlov632bi.apps.googleusercontent.com";
           HttpTransport transport = new NetHttpTransport();
           JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

           GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                   .setAudience(Collections.singletonList(googleClientId))
                   .build();
           GoogleIdToken idToken = verifier.verify(requestDto.getToken());

        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            String userId = payload.getSubject();
            logger.debug("User ID: " + userId);

            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            logger.debug("Name: " + name);
            return getUserFromGoogle(email, givenName, familyName);
        } else {
            throw new GeneralException(" token.",HttpStatus.BAD_REQUEST);
        }
       }catch (Exception e){
           throw new GeneralException("Invalid ID token.",HttpStatus.BAD_REQUEST);
       }
    }

    private Customer getUserFromGoogle(String email, String firstName, String lastName) {
        Optional<Customer> byEmail = customerRepository.findByEmail(email);
        Customer user = null;

        if (!byEmail.isPresent()) {
            user = new Customer();
//            Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(() -> {
//                throw new ItemNotFoundException("Role not found");
//            });
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(Role.ROLE_USER);
            user.setEmail(email);

//            user.setPassword(passwordEncoder.encode(Helper.getRandomString(10)));
            user.setPassword(passwordEncoder.encode("google"));
            user.setRole(Role.ROLE_USER);
            user.setStatus(Status.ACTIVE);
//            user.setUserId(null);

//            user.setEmailConfirmed(true);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            customerRepository.save(user);

        } else {
            user = byEmail.get();
        }
        return user;
    }
}
