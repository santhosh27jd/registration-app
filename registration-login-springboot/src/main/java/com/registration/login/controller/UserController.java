package com.registration.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.registration.login.dto.UserRegistrationDto;
import com.registration.login.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserController {
	
	private UserService service;

	public UserController(UserService service) {
		super();
		this.service = service;
	}
	
	@ModelAttribute("user")
	public UserRegistrationDto getUserRegistrationDto() {
		return new UserRegistrationDto();
	}
	
	@GetMapping
	public String showRegistration() {
		return "registration"; 
	}
	
	@PostMapping
	public String registerAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) {
		service.save(registrationDto);
		return "redirect:/registration?success";
	}

}
