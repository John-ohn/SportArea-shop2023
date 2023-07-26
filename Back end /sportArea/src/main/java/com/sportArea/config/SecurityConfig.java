package com.sportArea.config;

import com.sportArea.entity.Permission;
import com.sportArea.security.JwtConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private final UserDetailsService userDetailsService;
    private final JwtConfigurer jwtConfigurer;

    @Autowired
    public SecurityConfig(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
//                .antMatchers(HttpMethod.GET, "/user/**").hasAnyAuthority(Permission.DEVELOPERS_READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/user/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/user/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
                .antMatchers( "/user/auth/loqin").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .apply(jwtConfigurer);
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers(HttpMethod.GET, "/user/**").hasAnyAuthority(Permission.DEVELOPERS_READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/user/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/user/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
//                .antMatchers( "/user/auth/loqin").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .apply(jwtConfigurer);
////                .formLogin()
////                .loginPage("/showMyLoginPage")
////                .loginProcessingUrl("/authenticateTheUser").permitAll()
////                .and()
////                .logout()
////                .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout", "POST"))
////                .invalidateHttpSession(true)
////                .clearAuthentication(true)
////                .deleteCookies("JSESSIONID")
////                .logoutSuccessUrl("/").permitAll();
//
//        return http.build();
//    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws  Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

//    @Bean
//    protected DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return daoAuthenticationProvider;
//    }


}
