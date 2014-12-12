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
    public TestEnv save(TestEnv testEnv) {
        entityManager.persist(testEnv);
        return testEnv;
    }
    
    @Transactional
    public TestEnv update(TestEnv testEnv) {
        entityManager.refresh(testEnv);
        return testEnv;
    }

    public List<TestEnv> findAll() {
        return entityManager.createQuery("SELECT i FROM TestEnv i", TestEnv.class).getResultList();
    }

    public TestEnv findById(int id) {
        return entityManager.find(TestEnv.class, id);
    }
}
