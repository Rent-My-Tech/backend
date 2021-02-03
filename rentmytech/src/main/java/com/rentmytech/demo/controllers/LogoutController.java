package com.rentmytech.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogoutController
{
    @Autowired
    private TokenStore tokenStore;


    @GetMapping (value = {"/oauth/revoke-token", "/logout"}, produces = "application/json")
    public ResponseEntity<?> logout(HttpServletRequest request)
    {
        String authenticationHeader = request.getHeader("Authorization");
        if(authenticationHeader !=null)
        {
            String tokenValue = authenticationHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
