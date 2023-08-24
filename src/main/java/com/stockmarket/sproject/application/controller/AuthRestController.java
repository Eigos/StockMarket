package com.stockmarket.sproject.application.controller;

import org.springframework.web.bind.annotation.RestController;

import com.stockmarket.sproject.Security.Jwt.CustomUserDetailsService;
import com.stockmarket.sproject.Security.Jwt.Dto.AuthRequest;
import com.stockmarket.sproject.Security.Jwt.Dto.SignUpRequest;
import com.stockmarket.sproject.Security.Jwt.Dto.TokenResponse;
import com.stockmarket.sproject.Security.Jwt.Jwt.JwtUtil;
import com.stockmarket.sproject.application.Service.AccountService;
import com.stockmarket.sproject.application.dto.UserUpdateRequest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @Autowired
    private AccountService accountService;

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
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        
        if(userDetailsService.loadUserByUsername(signUpRequest.getEmail()) != null){
            return "User allready exists.";
        }

        userDetailsService.createAccount(signUpRequest);

        return "200";
    }

    @PostMapping("/admin/user")
    public String createUser(@Valid @RequestBody SignUpRequest signUpRequest) throws Exception {
        return signUp(signUpRequest);
    }

    @PatchMapping("/admin/user")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateRequest updateRequest) {

        accountService.UpdateAccount(updateRequest);

        return ResponseEntity.ok().build();
    }

}
