/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.ejb;

import com.albert.studybro.domain.dao.GeneralDaoLocal;
import com.albert.studybro.domain.dao.LifeAreaCategoryFacadeLocal;
import com.albert.studybro.domain.dao.LifeAreaFacadeLocal;
import com.albert.studybro.domain.dao.TaskFacadeLocal;
import com.albert.studybro.domain.dao.TaskTypeFacadeLocal;
import com.albert.studybro.domain.dao.WorkSessionFacadeLocal;
import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.LifeAreaCategory;
import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.TaskType;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.entities.WorkSession;
import com.albert.studybro.domain.entities.interfaces.IHaveParent;
import com.albert.studybro.events.ActivityEvent;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.joda.time.DateTime;

/**
 *
 * @author espen1
 */
@Stateless
public class RequestHandler implements RequestHandlerLocal {
    
    @Inject
    WorkSessionFacadeLocal workSessionDao;
    @Inject
    TaskTypeFacadeLocal taskTypeDao;
    @Inject
    TaskFacadeLocal taskDao;
    @Inject
    LifeAreaFacadeLocal lifeAreaDao;
    @Inject
    LifeAreaCategoryFacadeLocal lifeAreaCategoryDao;
    @Inject
    TaskPlannerLocal taskPlanner;
    @Inject
    GeneralDaoLocal generalDao;
    @Inject
    Event<ActivityEvent> activityEvents;

    @Override
    public void delete(Object deleteObject) {
        if (deleteObject instanceof IHaveParent) {
            ((IHaveParent) deleteObject).removeFromParent();
        }
        generalDao.delete(deleteObject);
    }

    @Override
    public void edit(Object o) {
        if(o instanceof IHaveParent) ((IHaveParent)o).updateParent();
        generalDao.edit(o);
    }

    @Override
    public <T extends Object> T find(Object id, Class<T> type) {
        return generalDao.find(id, type);
    }
    @Override
    public String createTaskType(User user, String newName, String newDescription) {
        if (taskTypeDao.findForUserWithName(user, newName) != null) {
            return "You already have a task type named that!";
        }
        TaskType newTask = new TaskType(user, newName);
        generalDao.create(newTask);
        activityEvents.fire(new ActivityEvent("New Task type", newTask, "taskOrLifeAreaCategoryEditor.xhtml"));
        user.getTaskTypeList().add(newTask);

        return "Created " + newName;
    }

    @Override
    public String createLifeArea(User user, String newName, String newDescription) {
        LifeArea findIdForUserWithName = lifeAreaDao.findIdForUserWithName(user, newName);
        if (findIdForUserWithName != null) {
            return "There is already a registered life area category with name: " + newName;
        }
        LifeArea lifeArea = new LifeArea(user, newName, newDescription);
        generalDao.create(lifeArea);
        activityEvents.fire(new ActivityEvent("New Life Area", lifeArea, "taskOrLifeAreaCategoryEditor.xhtml"));
        return "Created: " + newName;
    }


    @Override
    public void createTask(User user, TaskType selectedTaskType, LifeAreaCategory selectedLifeAreaCategory, String taskName, String taskDescription, Date dueDate, int importance, int joy, int estimatedHours, int estimatedMinutes) {
        Task task = new Task(user, selectedTaskType, selectedLifeAreaCategory, taskName, taskDescription, dueDate,
                importance, joy, estimatedHours, estimatedMinutes);
        generalDao.create(task);
        activityEvents.fire(new ActivityEvent("New task", task, "taskEditor.xhtml"));
    }
    @Override
    public String createLifeAreaCategory(User user, String newName, LifeArea selectedLifeArea) {
        for (LifeAreaCategory lac : selectedLifeArea.getLifeareaCategoryList()) {
            if (newName.equals(lac.getName())) {
                return "Same name not allowed!";
            }
        }
        LifeAreaCategory lifeAreaCategory = new LifeAreaCategory(newName, selectedLifeArea);
        generalDao.create(lifeAreaCategory);
        activityEvents.fire(new ActivityEvent("New Life Area Category", lifeAreaCategory,
                "taskOrLifeAreaCategoryEditor.xhtml"));

        return "Created: " + newName;
    }
    @Override
    public void createWorksession(Task task, DateTime startOnTask, DateTime finishWorkSession, User user, String notes) {
        WorkSession workSession = new WorkSession(task, startOnTask, finishWorkSession, user, notes);
        generalDao.create(workSession);
        task.getWorkSessionList().add(workSession);
        user.getTaskList().get(user.getTaskList().indexOf(task)).getWorkSessionList().add(workSession);
        edit(task);
        activityEvents.fire(new ActivityEvent("Create worksession", workSession, "workSessionEditor.xhtml"));
    }

    @Override
    public List<LifeAreaCategory> findLifeAreaCategoriesForUser(LifeArea lifeArea) {
        return lifeAreaCategoryDao.findLifeAreaCategoriesForLifeAreas(lifeArea);
    }
    @Override
    public List<TaskType> findTaskTypesForUser(User user) {
        return user.getTaskTypeList();
    }

    @Override
    public List<LifeArea> findLifeAreasForUser(User user) {
        return lifeAreaDao.findRowsForUser(user);
    }


    @Override
    public List<Task> findTasksForUser(User user) {
        return taskDao.findRowsForUser(user);
//        return user.getTaskList();
    }

    @Override
    public List<Task> findSortedTasksForUser(User user) {
        return taskPlanner.planTasks(findTasksForUser(user));
    }

    @Override
    public List<WorkSession> findWorkSessionsForUser(User user) {
        return workSessionDao.findRowsForUser(user);
    }
    
    @Override
    public void completeTask(Task task) {
        task.setComplete(true);
        edit(task);
        FacesContext.getCurrentInstance().addMessage("mainForm", new FacesMessage(FacesMessage.SEVERITY_INFO,
                "YEAH! Finished task: " + task.getName(), ""));
        activityEvents.fire(new ActivityEvent("Task complete", task, "taskEditor.xhtml"));
    }

    @Override
    public void createUser(User user) {
        generalDao.create(user);
    }

    @Override
    public List<WorkSession> findWorkSessions(Task t) {
        return workSessionDao.findRowsForTask(t);
    }


}
