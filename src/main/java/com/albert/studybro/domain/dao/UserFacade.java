package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.entities.User_;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author espen1
 */
@Stateless
public class UserFacade implements UserFacadeLocal {

    @PersistenceContext(unitName = "com.albert_StudyBro_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public UserFacade() {
    }

    @Override
    public User find(String username) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        Predicate sameUser = cb.equal(user.get(User_.username), username);
        cq.where(sameUser);
        List<User> resultList = em.createQuery(cq).getResultList();
        return resultList.size() < 1 ? null : resultList.get(0);

    }
}
