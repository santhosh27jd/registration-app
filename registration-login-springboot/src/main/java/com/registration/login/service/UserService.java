package com.registration.login.service;


import org.springframework.security.core.userdetails.UserDetailsService;

import com.registration.login.dto.UserRegistrationDto;
import com.registration.login.model.User;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
