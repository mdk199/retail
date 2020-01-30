package com.retail.service;

import com.retail.entity.Role;
import com.retail.entity.User;
import com.retail.repository.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * @param username
	 * @return
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User " + username + "  does not exist. ");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getGrantedAuthorities(user));
	}

	/**
	 * Assign Roles to the user.
	 * 
	 * @param user
	 * @return
	 */
	private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
		Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();
		List<Role> roles = user.getRoles();

		grantedAuthority.add(new SimpleGrantedAuthority("ROLE_USER"));
		for (Role role : roles) {
			if (role.getName().equalsIgnoreCase("admin")) {
				grantedAuthority.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			}
		}

		return grantedAuthority;
	}

}
