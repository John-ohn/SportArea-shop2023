package com.sportArea.controller;


import com.sportArea.entity.User;
import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.entity.dto.restJWT.AuthenticationRequestDTO;
import com.sportArea.security.JwtTokenProvider;
import com.sportArea.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager,
                                        UserService userService,
                                        JwtTokenProvider jwtTokenProvider,
                                        GeneralLogg generalLogg) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.generalLogg = generalLogg;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            User user = userService.findByEmail(request.getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User doesn't exists"));

            String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name(), user.getUserId());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", request.getEmail());
            response.put("token", token);
            response.put("userId", user.getUserId());

            // Make Logger info
            generalLogg.getLoggerControllerInfo("AuthenticationRestController",
                    "authenticate",
                    "/login",
                    "jwtToken to login user "+request.getEmail());

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

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Enumeration<String> name = request.getSession().getAttributeNames();
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);

        generalLogg.getLoggerControllerInfo("AuthenticationRestController",
                "logout",
                "/logout",
                "and close session of "+name.toString());
    }

}
