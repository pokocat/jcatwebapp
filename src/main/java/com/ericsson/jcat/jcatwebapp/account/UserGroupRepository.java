package com.ericsson.jcat.jcatwebapp.account;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class UserGroupRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public UserGroup save(UserGroup group) {
		entityManager.persist(group);
		return group;
	}

	@Transactional
	public UserGroup update(UserGroup group) {
		entityManager.refresh(group);
		return group;
	}

	public List<UserGroup> findAll() {
		return entityManager.createQuery("SELECT i FROM UserGroup i", UserGroup.class).getResultList();
	}

	public UserGroup findById(int id) {
		return entityManager.find(UserGroup.class, id);
	}

	public void deleteById(int id) {
		entityManager.remove(this.findById(id));
		entityManager.flush();
	}
}
