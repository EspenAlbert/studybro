package com.albert.studybro.faces;

import com.albert.studybro.domain.dao.TaskFacadeLocal;
import com.albert.studybro.domain.dao.WorkSessionFacadeLocal;
import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.entities.WorkSession;
import com.albert.studybro.ejb.RequestHandlerLocal;
import com.albert.studybro.ejb.TaskPlannerLocal;
import com.albert.studybro.interceptors.AddToObjectMapper;
import com.albert.studybro.interceptors.ArgumentHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import org.joda.time.DateTime;

/**
 *
 * @author espen1
 */
@Named
@ViewScoped
public class CheckInnBean extends SessionBean implements Serializable {

    @Size(max = 240)
    private String notes;
    private String errorMessage;
    private List<Integer> taskIds;
    private List<LifeArea> lifeAreas;
    private List<WorkSession> workSessions;
    private int currentTaskIndex;
    private DateTime startOnTask;

    @Inject
    RequestHandlerLocal requestHandler;
    @Inject
    TaskPlannerLocal taskPlanner;
    @Inject
    TaskFacadeLocal taskDao;
    @Inject
    WorkSessionFacadeLocal workSessionDao;

    public CheckInnBean() {
        workSessions = new ArrayList<>();
    }

    @PreDestroy
    private void removeContextParams() {
        sessionMap().remove("activeObject");
        sessionMap().remove("task");
    }

    @PostConstruct
    private void populate() {
        List<LifeArea> findLifeAreasForUser = requestHandler.findLifeAreasForUser(user);
        lifeAreas = findLifeAreasForUser;
        //When there is already a task existing in the context
        if (sessionMap().get("task") != null || sessionMap().get("activeObject") != null) {
            Task t;
            try { //An activeobject could be of different type, not task...
                t = (Task) (sessionMap().get("task") != null ? sessionMap().get("task") : sessionMap().get(
                        "activeObject"));
            } catch (ClassCastException e) {
                return;
            }
            taskIds = new ArrayList<Integer>();
            taskIds.add(t.getIdtask());
            taskIds.addAll(taskPlanner.planForLifeArea(t.getIdlifeareaCategory().getIdlifearea()));
            currentTaskIndex = 0;
            sessionMap().put(bundle.getString("selectedLifeArea"), t.getIdlifeareaCategory().getName());
            displayNextTask();
        }
    }

    @ArgumentHelper(resourceKeys = {"selectedLifeArea"})
    public void lifeAreaSelected(LifeArea lifeArea) {
        taskIds = taskPlanner.planForLifeArea(requestHandler.find(lifeArea.getIdlifearea(), LifeArea.class));
        currentTaskIndex = 0;
        displayNextTask();
    }

    public void completeTask() {
        requestHandler.completeTask((Task) sessionMap().get("task"));
        displayNextTask();
    }

    public void bringNextTask() {
        displayNextTask();
    }

    public void stayOnSameTask() {
        currentTaskIndex--;
        displayNextTask();
    }

    private void displayNextTask() {
        System.out.println("Getting next task!");
        DateTime finishWorkSession = new DateTime();
        if (saveWorkSession(finishWorkSession)) {
            System.out.println("Trying to save work session");
            requestHandler.createWorksession((Task) sessionMap().get("task"), startOnTask, finishWorkSession, user,
                    notes);
            sessionMap().put("user", requestHandler.find(user.getIduser(), User.class));
        }

        if (currentTaskIndex > (taskIds.size() - 1)) {
            noMoreTasks();
            return;
        }
        Task task = requestHandler.find(taskIds.get(currentTaskIndex), Task.class);
        sessionMap().put("task", task);
        workSessions = requestHandler.findWorkSessions(task);
        Collections.reverse(workSessions);
        startOnTask = new DateTime();
        notes = "";
        currentTaskIndex++;
    }

    private boolean saveWorkSession(DateTime finishWorkSession) {
        System.out.println("Start on task: " + startOnTask);
        if (startOnTask == null) {
            return false;
        }
        final int secondsSinceStartingTask = finishWorkSession.getSecondOfDay() - startOnTask.getSecondOfDay();
        System.out.println(notes + "seconds since starting: " + secondsSinceStartingTask);
        return notes.length() > 0 || secondsSinceStartingTask > 60;
    }

    private void noMoreTasks() {
        String selectedLifeArea = (String) sessionMap().get(bundle.getString("selectedLifeArea"));
        FacesContext.getCurrentInstance().addMessage("mainForm", new FacesMessage(FacesMessage.SEVERITY_INFO,
                "You are a boss! Finished every task for: " + selectedLifeArea, ""));
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @AddToObjectMapper
    public List<LifeArea> getLifeAreas() {
        return lifeAreas;
    }

    public void setLifeAreas(List<LifeArea> lifeAreas) {
        this.lifeAreas = lifeAreas;
    }

    public List<WorkSession> getWorkSessions() {
        return workSessions;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
