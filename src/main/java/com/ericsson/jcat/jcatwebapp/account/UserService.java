package com.ericsson.jcat.jcatwebapp.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserGroupRepository userGroupRepository;

	@PostConstruct
	protected void initialize() {
		accountRepository.save(new Account("eduowan", "demo", "Donis", new ArrayList<String>(Arrays.asList("JCAT", "RST")), "admin", "donis.wang@ericsson.com",
				"ROLE_USER"));
		accountRepository.save(new Account("admin", "admin", "admin", new ArrayList<String>(Arrays.asList("RST")), "admin", "b@c.d", "ROLE_ADMIN"));
		accountRepository.save(new Account("eyieguo", "demo", "Yingjie", new ArrayList<String>(Arrays.asList("CHS","JCAT")), "admin", "yingjie.guo@ericsson.com",
				"ROLE_ADMIN"));
		accountRepository.save(new Account("ewwgffg", "demo", "Maple Wang", new ArrayList<String>(Arrays.asList("JCAT")), "admin", "maple.wang@ericsson.com",
				"ROLE_ADMIN"));
		userGroupRepository.save(new UserGroup("CHS", "Characteristic Team"));
		userGroupRepository.save(new UserGroup("JCAT", "Jcat AXE Team"));
		userGroupRepository.save(new UserGroup("RST", "Regression Testing Team"));
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUserName(username);
		if (account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return createUser(account);
	}

	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}

	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null,
				Collections.singleton(createAuthority(account)));
	}

	private User createUser(Account account) {
		return new User(account.getUserName(), account.getPassword(), Collections.singleton(createAuthority(account)));
	}

	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}
