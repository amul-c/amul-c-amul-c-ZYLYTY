package com.productinventrymanagementapp.service;

import com.productinventrymanagementapp.dto.UserRequestDto;
import com.productinventrymanagementapp.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    ResponseEntity<?> registerUser(@RequestBody UserRequestDto request);
    UserResponseDto getUser(String email);
}
