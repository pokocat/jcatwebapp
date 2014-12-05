package com.ericsson.jcat.jcatwebapp.testenv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class TestEnvRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestEnvRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public TestEnv save(TestEnv instance) {
        entityManager.persist(instance);
        return instance;
    }

    public List<TestEnv> findAll() {
        return entityManager.createQuery("SELECT i FROM TestEnv i", TestEnv.class).getResultList();
    }

    public TestEnv findById(Long id) {
        return entityManager.find(TestEnv.class, id);
    }
}