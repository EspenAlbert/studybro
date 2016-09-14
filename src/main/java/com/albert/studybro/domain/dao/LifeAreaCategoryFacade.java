package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.LifeAreaCategory;
import com.albert.studybro.domain.entities.LifeAreaCategory_;
import com.albert.studybro.domain.entities.User;
import java.util.List;
import javax.ejb.Stateless;
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
@Stateless
public class LifeAreaCategoryFacade implements LifeAreaCategoryFacadeLocal {

    @PersistenceContext(unitName = "com.albert_StudyBro_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public LifeAreaCategoryFacade() {
    }

    public List<LifeAreaCategory> findLifeAreaCategoriesForLifeAreas(LifeArea lifearea) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<LifeAreaCategory> cq = cb.createQuery(LifeAreaCategory.class);
        Root<LifeAreaCategory> root = cq.from(LifeAreaCategory.class);
        Predicate p = cb.equal(root.get(LifeAreaCategory_.idlifearea), lifearea);
        cq.where(p);

        List<LifeAreaCategory> resultList = em.createQuery(cq).getResultList();
        return resultList;
    }

}
