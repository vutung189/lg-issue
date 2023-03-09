package com.app.service;

import com.app.config.security.SecurityUtils;
import com.app.dto.UserDTO;
import com.app.entity.Authority;
import com.app.entity.User;
import com.app.repository.AuthorityRepository;
import com.app.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

	private final Logger log = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthorityRepository authorityRepository;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthorityRepository authorityRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authorityRepository = authorityRepository;
	}
}
