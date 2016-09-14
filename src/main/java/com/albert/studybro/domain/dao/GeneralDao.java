package com.albert.studybro.domain.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author espen1
 */
@Stateless
public class GeneralDao implements GeneralDaoLocal {

    @PersistenceContext(unitName = "com.albert_StudyBro_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    public void delete(Object object) {
        Object merge = em.merge(object);
        em.remove(merge);
    }

    @Override
    public void edit(Object o) {
        em.merge(o);
    }

    @Override
    public void create(Object entity) {
        em.persist(entity);
    }

    @Override
    public <T extends Object> T find(Object id, Class<T> type) {
        return em.find(type, id);
    }

}
