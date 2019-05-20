package com.pharos.myposts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharos.myposts.entities.User;
import com.pharos.myposts.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	private String loggedUsername;

	public boolean valiateUser(String userName, String password) {
		User user = userRepository.findByUsername(userName);
		setLoggedUsername(userName);
		return user != null && encoder.matches(password, encoder.encode(user.getPassword()));
	}

	public User findByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username + " was not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), encoder.encode(user.getPassword()),
				AuthorityUtils.createAuthorityList());
	}

	public String getLoggedUsername() {
		return loggedUsername;
	}

	public void setLoggedUsername(String loggedUsername) {
		this.loggedUsername = loggedUsername;
	}
}
