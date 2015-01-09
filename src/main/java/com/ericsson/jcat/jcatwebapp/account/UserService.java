package com.ericsson.jcat.jcatwebapp.account;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;

import com.ericsson.jcat.jcatwebapp.cusom.UserGroup;

public class UserService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@PostConstruct
	protected void initialize() {
		accountRepository.save(new Account("user", "demo","xxx", "CHS", "a@b.c","ROLE_USER"));
		accountRepository.save(new Account("admin", "admin","xxxx", "RST","b@c.d", "ROLE_ADMIN"));
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
