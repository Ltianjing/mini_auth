package com.example.auth_server.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.auth_server.service.JwtService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nimbusds.jose.JOSEException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtService jwtService,AuthenticationManager authenticationManager){
        this.jwtService =jwtService;
        this.authenticationManager = authenticationManager;
    }



    @PostMapping("/auth/token")
    public Object login(String username, String password) throws JOSEException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthentionToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthentionToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> authorityList = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = jwtService.generateJwtToken(username, authorityList);
        TokenDto tokenDto = new TokenDto();
        tokenDto.accessToken = token;
        return tokenDto; 
    }

    @GetMapping("/auth/jwt")
    public Object getJwt(){
        return jwtService.GetJwkSet().toJSONObject();
    }

    class TokenDto {
        @JsonProperty(value = "access_token")
        public String accessToken;
    }
  
}
