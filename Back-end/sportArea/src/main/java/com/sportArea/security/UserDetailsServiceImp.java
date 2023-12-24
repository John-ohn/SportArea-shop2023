package com.sportArea.security;

import com.sportArea.entity.Customer;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service("userDetailsServiceImpl")
public class UserDetailsServiceImp implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(UserDetailsServiceImp.class);

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = userService.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User with this email doesn't exists, you can't get Authentication ")
        );
        logger.info("From UserDetailsServiceImp method -loadUserByUsername- check if email exists: {} ", username);

        return SecurityUser.fromUser(customer);
    }
}
