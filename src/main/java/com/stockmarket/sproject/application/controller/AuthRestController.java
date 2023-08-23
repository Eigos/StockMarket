package com.stockmarket.sproject.application.controller;

import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.sproject.Security.Jwt.CustomUserDetailsService;
import com.stockmarket.sproject.Security.Jwt.Dto.AuthRequest;
import com.stockmarket.sproject.Security.Jwt.Dto.SignUpRequest;
import com.stockmarket.sproject.Security.Jwt.Dto.TokenDto;
import com.stockmarket.sproject.Security.Jwt.Jwt.JwtUtil;

import io.jsonwebtoken.Claims;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class AuthRestController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> creteToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorret username or password", ex);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        TokenDto jwtDto = jwtUtil.generateTokenDto(userDetails);

        return new ResponseEntity<TokenDto>(jwtDto, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        
        if(userDetailsService.loadUserByUsername(signUpRequest.getEmail()) != null){
            return "User allready exists.";
        }

        userDetailsService.createAccount(signUpRequest);

        return "200";
    }


}
