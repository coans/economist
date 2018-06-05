package com.thera.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.thera.db.repository.UserRepository;

@Service("authorizationService")
public class AuthorizationService implements UserDetailsService {

	private static UserRepository userRepository;

	@Autowired
	public AuthorizationService(UserRepository userRepository) {
		Assert.notNull(userRepository, "User repo must not be null.");
		AuthorizationService.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException, DataAccessException {
		try {
			final com.thera.db.entity.User user = userRepository.findActiveByEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException("User [" + email + "] not found");
			}
			return new User(user.getEmail(), user.getPassword(), true, true, true, true, getGrantedAuthorities(user));
		} catch (Exception e) {
			throw new UsernameNotFoundException("User [" + email + "] not found");
		}
	}

	private List<GrantedAuthority> getGrantedAuthorities(com.thera.db.entity.User user) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return grantedAuthorities;
	}

	public static com.thera.db.entity.User getAuthentificatedUser() {
		try {
			com.thera.db.entity.User theraUser = null;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof UserDetails) {
				UserDetails details = (UserDetails) principal;
				theraUser = userRepository.findActiveByEmail(details.getUsername());
			}
			return theraUser;
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found");
		}
	}
	
	public boolean loginWithUsername(String email) {
		UserDetails userDetails = loadUserByUsername(email);
		if (userDetails == null) {
			return false;
		}
		Authentication authentication =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return true;
	}
}