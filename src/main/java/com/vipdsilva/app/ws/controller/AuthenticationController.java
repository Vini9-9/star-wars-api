package com.vipdsilva.app.ws.controller;

import com.vipdsilva.app.ws.config.security.TokenService;
import com.vipdsilva.app.ws.model.request.LoginFormModel;
import com.vipdsilva.app.ws.model.response.TokenDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;
    
    @PostMapping
    public ResponseEntity<TokenDto> authenticate (@RequestBody LoginFormModel form) {

        UsernamePasswordAuthenticationToken dataLogin = form.convert();
        
        try {

            Authentication authentication = authManager.authenticate(dataLogin);
            String token = tokenService.generateToken(authentication);
            return new ResponseEntity<TokenDto>(new TokenDto(token, "Bearer"), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    } 
}
