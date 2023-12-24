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
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;


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
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/users/testAuth").hasAnyAuthority(Permission.DEVELOPERS_READ.getPermission())
                .antMatchers(HttpMethod.GET,
                        "/response/**",
                        "/oauth2",
                        "/api/v1/users",
                        "/api/v1/users/{userId}",
                        "/api/v1/users/registration",
                        "/api/v1/users/welcome",
                        "/api/v1/products/**",
                        "/api/v1/comments/**",
                        "/api/v1/orders/**",
                        "/api/v1/baskets/**",
                        "/api/v1/blogs/**",
                        "/api/v1/categorys/**",
                        "/api/v1/delivery/address/**",
                        "/api/v1/post/**",
                        "/api/users/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/users/**",
                        "/api/v1/user/auth/login",
                        "/api/v1/products/**",
                        "/api/v1/comments/**",
                        "/api/v1/orders/**",
                        "/api/v1/baskets/**",
                        "/api/v1/blogs/**",
                        "/api/v1/categorys/**",
                        "/api/v1/delivery/address/**",
                        "/api/v1/post/**",
                        "/api/v1/email/**",
                        "/api/users/**").permitAll()
                .antMatchers(HttpMethod.PUT,
                        "/api/v1/users/**",
                        "/api/v1/delivery/address/**").permitAll()
                .antMatchers(HttpMethod.PATCH,
                        "/api/v1/users/**",
                        "/api/v1/delivery/address/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/users/**",
                        "/api/v1/products/**",
                        "/api/v1/orders/**",
                        "/api/v1/baskets/**",
                        "/api/v1/blogs/**",
                        "/api/v1/categorys/**",
                        "/api/v1/delivery/address/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/user/**").hasAnyAuthority(Permission.DEVELOPERS_READ.getPermission())
//                .antMatchers(HttpMethod.POST, "/user/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE, "/user/**").hasAuthority(Permission.DEVELOPERS_WRITE.getPermission())
                .anyRequest()
                .authenticated()
//                .and()
//                .apply(jwtConfigurer)
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization/google")
                .and()
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/google");
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public OAuth2AuthorizedClientRepository authorizedClientRepository(
            ClientRegistrationRepository clientRegistrationRepository) {
        return new HttpSessionOAuth2AuthorizedClientRepository();
    }

}
