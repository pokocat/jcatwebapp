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
		UserGroup ug1 = new UserGroup("JCAT", "test");
		UserGroup ug2 = new UserGroup("RST", "test");
		UserGroup ug3 = new UserGroup("CHS", "test");
		// userGroupRepository.save(ug1);
		// userGroupRepository.save(ug2);
		// userGroupRepository.save(ug3);
		Account account1 = new Account();
		account1.setUserName("admin");
		account1.setPassword("admin");
		account1.setNickName("admin");
		account1.getUserGroup().addAll(Arrays.asList(ug1, ug2, ug3));
		account1.setGroupRole("admin");
		account1.setEmail("a@b.c");
		account1.setRole("ROLE_ADMIN");
		// accountRepository.save(new Account("admin", "admin", "admin", new ArrayList<UserGroup>(Arrays.asList(ug2)),
		// "admin", "b@c.d", "ROLE_ADMIN"));

		Account account2 = new Account("eduowan", "demo", "Donis", "admin", "donis.wang@ericsson.com", "ROLE_USER");

		Account account3 = new Account("eyieguo", "demo", "Yingjie", "admin", "yingjie.guo@ericsson.com", "ROLE_ADMIN");

		accountRepository.save(account1);
		accountRepository.save(account2);
		account2.setUserGroup(Arrays.asList(ug1));
		accountRepository.update(account2);
		accountRepository.save(account3);
		account3.getUserGroup().addAll(Arrays.asList(ug1, ug2, ug3));
		accountRepository.update(account3);
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
