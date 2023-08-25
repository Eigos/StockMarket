package com.stockmarket.sproject.application.controller;

import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.sproject.Security.Jwt.CustomUserDetailsService;
import com.stockmarket.sproject.Security.Jwt.Dto.AuthRequest;
import com.stockmarket.sproject.Security.Jwt.Dto.SignUpRequest;
import com.stockmarket.sproject.Security.Jwt.Dto.TokenResponse;
import com.stockmarket.sproject.Security.Jwt.Jwt.JwtUtil;
import javax.validation.Valid;

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

    private JwtUtil jwtUtil;

    private AuthenticationManager authenticationManager;

    private CustomUserDetailsService userDetailsService;

    AuthRestController(
        JwtUtil jwtUtil,
        AuthenticationManager authenticationManager,
        CustomUserDetailsService userDetailsService
    ){
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> creteToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorret username or password", ex);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        TokenResponse jwtDto = jwtUtil.generateTokenDto(userDetails);

        return new ResponseEntity<TokenResponse>(jwtDto, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        
        if(userDetailsService.loadUserByUsername(signUpRequest.getEmail()) != null){
            return ResponseEntity.badRequest().body("User already exists.");
        }

        userDetailsService.createAccount(signUpRequest);

        return ResponseEntity.ok().build();
    }

}
