package com.sportArea.restJWT;


import com.sportArea.entity.User;
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
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/user/auth")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/loqin")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            User user = userService.findByEmail(request.getEmail()).orElseThrow(
                    () -> new UsernameNotFoundException("User doesn't exists")
            );
            String token = jwtTokenProvider.createToken(request.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", request.getEmail());
            response.put("token", token);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {

            return new ResponseEntity<>("Invalid email/password combination", HttpStatus.FORBIDDEN);

        }
    }

    @PostMapping("/loqout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

}
