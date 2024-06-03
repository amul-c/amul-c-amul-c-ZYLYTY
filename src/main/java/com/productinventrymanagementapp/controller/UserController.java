package com.productinventrymanagementapp.controller;

import com.productinventrymanagementapp.config.JwtUtil;
import com.productinventrymanagementapp.dto.UserRequestDto;
import com.productinventrymanagementapp.dto.UserResponseDto;
import com.productinventrymanagementapp.model.User;
import com.productinventrymanagementapp.response.ResponseHandler;
import com.productinventrymanagementapp.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDto request){
        try{
            return userService.registerUser(request);
        }catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody UserRequestDto user, HttpServletResponse response) {
        Authentication authObj;
        try {
            authObj = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail().toLowerCase(), user.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, "Invalid Password");
        }
        String token = jwtUtil.generateToken(authObj);

        // Set token as cookie
        Cookie cookie = new Cookie("session", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Use true if using HTTPS
        cookie.setPath("/");
        response.addCookie(cookie);
        UserResponseDto res = userService.getUser(user.getEmail());
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

}
