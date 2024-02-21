package com.sportArea.controller;


import com.sportArea.entity.Customer;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.entity.dto.restJWT.AuthenticationGoogleRequestDto;
import com.sportArea.entity.dto.restJWT.AuthenticationRequestDTO;
import com.sportArea.exception.GeneralException;
import com.sportArea.security.JwtTokenProvider;
import com.sportArea.service.CustomerService;
import com.sportArea.service.GoogleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/user/auth")
public class AuthenticationRestController {

    private final GeneralLogg generalLogg;
    private final AuthenticationManager authenticationManager;
    private final CustomerService customerService;
    private final JwtTokenProvider jwtTokenProvider;
    private final GoogleUserService googleUserService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        CustomerService customerService,
                                        GoogleUserService googleUserService,
                                        JwtTokenProvider jwtTokenProvider,
                                        GeneralLogg generalLogg) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.googleUserService = googleUserService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.generalLogg = generalLogg;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            Customer customer = customerService.findByEmail(request.getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User doesn't exists"));

            String token = jwtTokenProvider.createToken(request.getEmail(), customer.getRole().name(), customer.getUserId());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", request.getEmail());
            response.put("token", token);
            response.put("userId", customer.getUserId());

            // Make Logger info
            generalLogg.getLoggerControllerInfo("AuthenticationRestController",
                    "authenticate",
                    "/login",
                    "jwtToken to login user " + request.getEmail());

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {

            // Make Logger war
            generalLogg.getLoggerControllerWarn("AuthenticationRestController",
                    "authenticate",
                    "/login",
                    "Invalid email/password combination",
                    HttpStatus.FORBIDDEN);

            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);

        }
    }

    @PostMapping("/login/google")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationGoogleRequestDto requestDto) throws Exception {
//        try {
            Customer user = googleUserService.loginGoogle(requestDto);
            String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().toString(),user.getUserId());
//            JwtResponseDto response = new JwtResponseDto(user.getEmail(), token, user.getId());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", user.getEmail());
            response.put("token", token);
            response.put("userId", user.getUserId());
            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            throw new GeneralException(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
    }



    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Enumeration<String> name = request.getSession().getAttributeNames();
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);

        generalLogg.getLoggerControllerInfo("AuthenticationRestController",
                "logout",
                "/logout",
                "and close session of " + name.toString());
    }

}
