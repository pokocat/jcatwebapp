package com.ericsson.jcat.jcatwebapp.testenv;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ericsson.jcat.jcatwebapp.account.UserGroup;

@Repository
@Transactional(readOnly = true)
public class StpInfoRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(StpInfoRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public StpInfo save(StpInfo stpInfo) {
		entityManager.persist(stpInfo);
		return stpInfo;
	}

	@Transactional
	public StpInfo update(StpInfo stpInfo) {
		entityManager.refresh(stpInfo);
		return stpInfo;
	}

	public List<StpInfo> findAll() {
		return entityManager.createQuery("SELECT i FROM StpInfo i", StpInfo.class).getResultList();
	}

	public StpInfo findById(int id) {
		return entityManager.find(StpInfo.class, id);
	}
	
	public StpInfo findByName(String name){
		return entityManager.createQuery("SELECT i FROM StpInfo i where i.stpName = :stpName", StpInfo.class).setParameter("stpName", name).getSingleResult();
	}

	public void deleteById(int id) {
		entityManager.remove(this.findById(id));
		entityManager.flush();
	}
}
