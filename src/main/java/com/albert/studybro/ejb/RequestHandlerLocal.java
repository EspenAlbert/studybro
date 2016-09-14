/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.ejb;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.LifeAreaCategory;
import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.TaskType;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.entities.WorkSession;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.joda.time.DateTime;

/**
 *
 * @author espen1
 */
@Local
public interface RequestHandlerLocal {

    public List<LifeAreaCategory> findLifeAreaCategoriesForUser(LifeArea lifeArea);

    public void createTask(User user, TaskType selectedTaskType, LifeAreaCategory selectedLifeAreaCategory, String taskName, String taskDescription, Date dueDate, int importance, int joy, int estimatedHours, int estimatedMinutes);

    public List<Task> findTasksForUser(User user);
    
    public List<WorkSession> findWorkSessionsForUser(User user);

    public List<Task> findSortedTasksForUser(User user);

    public void delete(Object deleteObject);

    public void edit(Object o);

    public <T extends Object> T find(Object id, Class<T> type);

    public void completeTask(Task task);

    public void createWorksession(Task task, DateTime startOnTask, DateTime finishWorkSession, User user, String notes);

    public void createUser(User user);

    public List<WorkSession> findWorkSessions(Task t);

    List<TaskType> findTaskTypesForUser(User user);

    List<LifeArea> findLifeAreasForUser(User user);

    public String createLifeAreaCategory(User user, String newName, LifeArea selectedLifeArea);

    public String createTaskType(User user, String newName, String newDescription);

    public String createLifeArea(User user, String newName, String newDescription);
}
