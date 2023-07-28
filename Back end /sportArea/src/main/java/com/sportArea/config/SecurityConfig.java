package com.sportArea.config;

import com.sportArea.entity.Permission;
import com.sportArea.security.JwtConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
                .antMatchers( "/user/auth/loqin").permitAll()
                .antMatchers(HttpMethod.POST, "/user/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/user/**").hasAnyAuthority(Permission.DEVELOPERS_READ.getPermission())
                .antMatchers(HttpMethod.POST, "/user/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
                .antMatchers(HttpMethod.DELETE, "/user/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
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

//    @Bean
//    protected DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        return daoAuthenticationProvider;
//    }


}
