package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.LifeAreaCategory;
import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.Task_;
import com.albert.studybro.domain.entities.User;
import java.util.ArrayList;
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
public class TaskFacade extends TableWithUser<Task> implements TaskFacadeLocal {

    @PersistenceContext(unitName = "com.albert_StudyBro_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    /**
     *
     */
    public TaskFacade() {
        super(Task.class);
    }

    @Override
    public List<Task> selectTasksForLifeArea(LifeArea lifeArea) {
        List<LifeAreaCategory> categories = lifeArea.getLifeareaCategoryList();
        ArrayList<Task> tasks = new ArrayList<>();
        for (LifeAreaCategory category : categories) {
            tasks.addAll(selectTasksForLifeAreaCategory(category));
        }
        return tasks;

    }

    private List<Task> selectTasksForLifeAreaCategory(LifeAreaCategory category) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> tasks = cq.from(Task.class);
        Predicate sameCategory = cb.equal(tasks.get(Task_.idlifeareaCategory), category);
        cq.where(sameCategory);
        List<Task> resultList = em.createQuery(cq).getResultList();
        return resultList;
    }

    @Override
    protected Path<User> getUserColumn(Root<Task> root) {
        return root.get(Task_.iduser);
    }

    @Override
    protected Path<String> getNameColumnPath(Root<Task> root) {
        return root.get(Task_.name);
    }

    @Override
    protected Path<Integer> getIdPath(Root<Task> root) {
        return root.get(Task_.idtask);
    }

}
