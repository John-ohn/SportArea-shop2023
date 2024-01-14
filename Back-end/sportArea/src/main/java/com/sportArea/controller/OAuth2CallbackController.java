package com.sportArea.controller;

import com.sportArea.entity.dto.logger.GeneralLogg;
import com.sportArea.security.JwtTokenProvider;

import com.sportArea.service.GoogleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.Map;

@RestController
public class OAuth2CallbackController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private GoogleUserService googleUserService;

    @Autowired
    private  GeneralLogg generalLogg;


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
    public ResponseEntity<?> googleAuthorization(@AuthenticationPrincipal OAuth2User     principal) {
//        String reactHomePageUrl = "http://localhost:5000";

        generalLogg.getLoggerControllerInfo("OAuth2CallbackController",
                "googleAuthorization",
                "/oauth2/authorization/google",
                "message (Try get OAuth2User.)"+principal.getAttributes());

//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(reactHomePageUrl);

//        Map<Object,Object> response = googleUserService.getJwtTokenFromGoogle(principal);
//
//        generalLogg.getLoggerControllerInfo("OAuth2CallbackController",
//                "googleAuthorization",
//                "/oauth2/authorization/google",
//                "JWT Token from google authorization form. And redirect on home page."+response.toString());

        return ResponseEntity.ok(principal);
//
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .location(builder.queryParam("jwt", response).build().toUri()).build();
    }
}
