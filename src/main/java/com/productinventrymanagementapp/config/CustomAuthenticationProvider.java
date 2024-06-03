package com.productinventrymanagementapp.config;

import com.productinventrymanagementapp.constants.ApplicationConstants;
import com.productinventrymanagementapp.model.User;
import com.productinventrymanagementapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private UserRepository userRepo;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        User customer = userRepo.findByEmail(username);
        if (customer != null) {
            if (passwordEncoder.matches(pwd, customer.getPassword())) {
                return new UsernamePasswordAuthenticationToken(username, pwd, Collections.emptyList());
            } else {
                throw new BadCredentialsException(ApplicationConstants.UNAUTHORIZED);
            }
        }else {
            throw new BadCredentialsException("No user registered with this details!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
