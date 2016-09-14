package com.albert.studybro.faces;

import com.albert.studybro.domain.entities.LifeAreaCategory;
import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.TaskType;
import com.albert.studybro.ejb.RequestHandlerLocal;
import com.albert.studybro.ejb.TaskPlannerLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author espen1
 */
@Named
@SessionScoped
public class PlannerBean extends SessionBean {

    private List<Task> tasks;

    public PlannerBean() {
    }

    @Inject
    RequestHandlerLocal requestHandler;
    @Inject
    TaskPlannerLocal taskPlanner;

    @PostConstruct
    private void populate() {
        tasks = requestHandler.findTasksForUser(user);
//        sessionMap().put("categories", user.getLifeareaList());
    }

    public String plan() {
        tasks = taskPlanner.planTasks(tasks);
        return "planning.xhtml?faces-redirect=true";
    }
    public List<Task> getTasks() {
        return tasks;
    }

    public void makeEditable(Task t) {
        t.setEditable(true);
    }
    
    public void saveChange(Task t) {
        t.setEditable(false);
        Integer lifeAreaCategoryId = Integer.parseInt((String) sessionMap().get(bundle.getString("selectedLifeAreaCategory")));
        Integer taskTypeId = Integer.parseInt((String) sessionMap().get(bundle.getString("selectedTaskType")));
        LifeAreaCategory selectLifeAreaCategory = requestHandler.find(lifeAreaCategoryId, LifeAreaCategory.class);
        TaskType selectedTaskType = requestHandler.find(taskTypeId, TaskType.class);
        t.setIdlifeareaCategory(selectLifeAreaCategory);
        t.setIdtaskType(selectedTaskType);
        requestHandler.edit(t);
        sessionMap().remove(bundle.getString("selectedLifeAreaCategory"));
        sessionMap().remove(bundle.getString("selectedTaskType"));
    }
    public String startWorking(Task t) {
        sessionMap().put("task", t);
        return "checkInn.xhtml";
    }
    public void delete(Task t) {
        tasks.remove(t);
        requestHandler.delete(t);
    }
}
