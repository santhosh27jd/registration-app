package com.registration.login.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.registration.login.dto.UserRegistrationDto;
import com.registration.login.model.Role;
import com.registration.login.model.User;
import com.registration.login.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		// TODO Auto-generated method stub
		User user = new User(registrationDto.getFirstName(), 
				registrationDto.getLastName(), 
				registrationDto.getEmail(), 
				passwordEncoder.encode(registrationDto.getPassword()), 
				Arrays.asList(new Role("ROLE_USER")));
		return repository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = repository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(),
				mapRolesToAuthority(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
