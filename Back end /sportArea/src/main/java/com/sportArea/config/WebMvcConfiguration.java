package com.sportArea.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.http.HttpHeaders;
import java.util.Arrays;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // Enable cross-origin request handling for the specified path pattern.
                // Exact path mapping URIs (such as "/admin") are supported as well as Ant-style path patterns (such as "/admin/**").
                .addMapping("/*")
                .allowedOrigins("*")
                // .allowedOriginPatterns("")
                .allowCredentials(false)
                .allowedHeaders("*")
                .exposedHeaders("*")
                .maxAge(60 *30)
                .allowedMethods("*")
        ;
    }

//    @Bean
//    public FilterRegistrationBean crosFitler(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("http://localhost:3000");
//        config.setAllowedHeaders(Arrays.asList(
//                HttpHeaders.AUTHORIZATION,
//                HttpHeaders.CONTENT_TYPE,
//                HttpHeaders.ACCEPT
//                ));
//        config.setAllowedMethods(Arrays.asList(
//                HttpMethod.GET.name(),
//                HttpMethod.POST.name(),
//                HttpMethod.PUT.name(),
//                HttpMethod.DELETE.name()
//                ));
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/**", config);
//        FilterRegistrationBean bean =new FilterRegistrationBean<>(new CorsFilter(source));
//        bean.setOrder(-102);
//        return bean;
//    }

}
