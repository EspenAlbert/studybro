package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.entities.WorkSession;
import com.albert.studybro.domain.entities.WorkSession_;
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
public class WorkSessionFacade extends TableWithUser<WorkSession> implements WorkSessionFacadeLocal {

    @PersistenceContext(unitName = "com.albert_StudyBro_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public WorkSessionFacade() {
        super(WorkSession.class);
    }

    @Override
    public List<WorkSession> findRowsForTask(Task t) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<WorkSession> cq = cb.createQuery(WorkSession.class);
        Root<WorkSession> root = cq.from(WorkSession.class);
        Predicate p = cb.equal(root.get(WorkSession_.idtask), t);
        cq.where(p);

        List<WorkSession> resultList = em.createQuery(cq).getResultList();
        return resultList;
    }

    @Override
    protected Path<User> getUserColumn(Root<WorkSession> root) {
        return root.get(WorkSession_.iduser);
    }

    @Override
    protected Path<String> getNameColumnPath(Root<WorkSession> root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Path<Integer> getIdPath(Root<WorkSession> root) {
        return root.get(WorkSession_.idworkSession);
    }
}
