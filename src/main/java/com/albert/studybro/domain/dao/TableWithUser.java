package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author espen1
 */
public abstract class TableWithUser<T> {

    protected Class<T> entityClass;

    @PersistenceContext(unitName = "com.albert_StudyBro_war_1.0-SNAPSHOTPU")
    protected EntityManager em;

    public TableWithUser(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract Path<User> getUserColumn(Root<T> root);

    protected abstract Path<String> getNameColumnPath(Root<T> root);

    protected abstract Path<Integer> getIdPath(Root<T> root);

    public T findIdForUserWithName(User user, String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        Predicate p = cb.equal(getNameColumnPath(root), name);
        Predicate p2 = cb.equal(getUserColumn(root), user);
        Predicate p3 = cb.and(p, p2);
        cq.where(p3);

        List<T> resultList = em.createQuery(cq).getResultList();
        return resultList.size() > 0 ? resultList.get(0) : null;

    }
    public List<T> findRowsForUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        Predicate p = cb.equal(getUserColumn(root), user);
        cq.where(p);

        List<T> resultList = em.createQuery(cq).getResultList();
        return resultList;

    }

}
