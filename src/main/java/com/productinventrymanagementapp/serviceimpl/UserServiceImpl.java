package com.productinventrymanagementapp.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.productinventrymanagementapp.constants.ApplicationConstants;
import com.productinventrymanagementapp.dto.UserRequestDto;
import com.productinventrymanagementapp.dto.UserResponseDto;
import com.productinventrymanagementapp.model.User;
import com.productinventrymanagementapp.repository.UserRepository;
import com.productinventrymanagementapp.response.ResponseHandler;
import com.productinventrymanagementapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> registerUser(UserRequestDto request) {
        if (userRepository.findByEmail(request.getEmail()) == null) {
            User user = objectMapper.convertValue(request,User.class);
            String hashPwd = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPwd);
            userRepository.save(user);
            return ResponseHandler.generateResponse(ApplicationConstants.CREATED,HttpStatus.CREATED,"Registration was successful.");
        }
        return ResponseHandler.generateResponse(ApplicationConstants.CONFLICT,HttpStatus.CONFLICT,ApplicationConstants.CONFLICT_ERROR_MESSAGE);
    }

    @Override
    public UserResponseDto getUser(String email) {
        User user = userRepository.findByEmail(email);
        return objectMapper.convertValue(user,UserResponseDto.class);
    }
}
