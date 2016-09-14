package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.TaskType;
import com.albert.studybro.domain.entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author espen1
 */
@Stateless
public class TaskTypeFacade implements TaskTypeFacadeLocal {

    @PersistenceContext(unitName = "com.albert_StudyBro_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    public TaskType findForUserWithName(User user, String newName) {
        List<TaskType> taskTypeList = user.getTaskTypeList();
        for(TaskType tt: taskTypeList) {
            if(tt.getName().equals(newName)) return tt;
        }
        return null;
    }

    public TaskTypeFacade() {
    }

}
