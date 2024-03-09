package com.sportArea.service;

import com.sportArea.entity.Customer;
import com.sportArea.entity.dto.GoogleUserDTO;
import com.sportArea.entity.dto.restJWT.AuthenticationGoogleRequestDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public interface GoogleUserService {

    void saveUserGoogle(GoogleUserDTO googleUser);

    Map<Object, Object> getJwtTokenFromGoogle (OAuth2User oauth2User);

     Customer loginGoogle(AuthenticationGoogleRequestDto requestDto) throws Exception;



}
