package com.sportArea.controller;

import com.sportArea.entity.Role;
import com.sportArea.entity.User;
import com.sportArea.entity.dto.GoogleUserDTO;
import com.sportArea.entity.dto.UserDTO;
import com.sportArea.security.JwtTokenProvider;

import com.sportArea.service.GoogleUserService;
import com.sportArea.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class OAuth2CallbackController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private GoogleUserService googleUserService;
    Logger logger = LoggerFactory.getLogger(OAuth2CallbackController.class);


    @GetMapping("/")
    @ResponseBody
    public String home() {

        return "<html>\n" +
                "<head>\n" +
                "    <title>Welcome</title>\n" +
                "    <script src=\"https://code.jquery.com/jquery-3.6.0.min.js\"></script>\n" +

//                "<script type=\"text/javascript\">\n" +
//                "    $.get(\"/oauth2\", function(data) {\n" +
//                "        $(\"#oauth2\").html(\"email: \" + data.email + \"<br>token: \" + data.token);\n" +
//                "        $(\".unauthenticated\").hide()\n" +
//                "        $(\".authenticated\").show()\n" +
//                "    });\n" +
//                "</script>"+
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container unauthenticated\">\n" +
                "        With Google: <a href=\"/oauth2/authorization/google\">click here</a>\n" +
                "    </div>\n" +
//                "    <div class=\"container authenticated\" style=\"display:none\">\n" +
//                "        Logged in as: <span id=\"oauth2\"></span>\n" +
//                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }

    @GetMapping("/oauth2/authorization/google")
    public ResponseEntity<?> user(@AuthenticationPrincipal OAuth2User principal) {

        Map<Object,Object> response = googleUserService.getJwtTokenFromGoogle(principal);

        return ResponseEntity.ok(response);
    }
}
