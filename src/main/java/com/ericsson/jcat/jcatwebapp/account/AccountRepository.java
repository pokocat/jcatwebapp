package com.ericsson.jcat.jcatwebapp.account;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class AccountRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		entityManager.persist(account);
		return account;
	}
	
	public Account findByUserName(String userName) {
		try {
			return entityManager.createNamedQuery(Account.FIND_BY_NAME, Account.class)
					.setParameter("userName", userName)
					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
	}
	
	public List<Account> findAll() {
        return entityManager.createQuery("SELECT i FROM Account i", Account.class).getResultList();
    }
	
}
