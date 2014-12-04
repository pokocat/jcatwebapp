package com.ericsson.jcat.jcatwebapp.instances;

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
public class InstancesRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstancesRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Instance save(Instance instance) {
        entityManager.persist(instance);
        return instance;
    }

    public List<Instance> findAll() {
        return entityManager.createQuery("SELECT i FROM Instance i", Instance.class).getResultList();
    }

    public Instance findById(Long id) {
        return entityManager.find(Instance.class, id);
    }
}
