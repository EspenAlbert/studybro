/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.faces;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.LifeAreaCategory;
import com.albert.studybro.domain.entities.TaskType;
import com.albert.studybro.ejb.RequestHandlerLocal;
import com.albert.studybro.interceptors.AddToObjectMapper;
import com.albert.studybro.interceptors.ArgumentHelper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author espen1
 */
@Named
@ViewScoped
public class AddTaskBean extends SessionBean implements Serializable {

    private Date dueDate;

    private int estimatedHours;
    private int estimatedMinutes;
    private int importance;
    private int joy;

    private String taskName;
    private String taskDescription;

    private String newName;
    private String newDescription;
    private String creationResult;
    private String selectedCategory;
    private String[] categoryTypes = new String[]{
        "Task Type", "Life Area", "Life area category"
    };
    private List<TaskType> taskTypes;
    private List<LifeArea> lifeAreas;
    private List<LifeAreaCategory> lifeAreaCategories;

    @Inject
    RequestHandlerLocal requestHandler;

    @PreDestroy
    private void removeContextParams() {
        sessionMap().remove(bundle.getString("selectedLifeArea"));
        sessionMap().remove(bundle.getString("selectedTaskType"));
    }

    @PostConstruct
    private void populate() {
        setTaskTypes(requestHandler.findTaskTypesForUser(user));
        setLifeAreas(requestHandler.findLifeAreasForUser(user));
        lifeAreaCategories = new ArrayList<LifeAreaCategory>();
        newName = "New category name";
        newDescription = "Description 1";
    }

    public void dummyListener() {

    }

    @ArgumentHelper(resourceKeys = {"selectedLifeArea"})
    public void lifeAreaSelected(LifeArea la2) throws Exception {
        if (la2 != null) {
            populateLifeAreaCategories(la2);
        }
    }

    public void populateLifeAreaCategories(LifeArea lifeArea) throws Exception {
        setLifeAreaCategories(requestHandler.findLifeAreaCategoriesForUser(lifeArea));
    }

    @ArgumentHelper(resourceKeys = {"selectedTaskType", "selectedLifeArea", "selectedLifeAreaCategory"})
    public String createTask(TaskType tt, LifeArea la, LifeAreaCategory lac) {
        requestHandler.createTask(user, tt, lac, taskName,
                taskDescription, dueDate, importance, joy, estimatedHours, estimatedMinutes);
        creationResult = "You have created: " + taskName + " successfully";
        return "";
    }

    @ArgumentHelper(resourceKeys = {"selectedLifeArea"})
    public void newCategoryType(LifeArea la) {
        if (selectedCategory.equals(categoryTypes[0])) {// Task type
            creationResult = requestHandler.createTaskType(user, newName, newDescription);
        } else if (selectedCategory.equals(categoryTypes[1])) {
            creationResult = requestHandler.createLifeArea(user, newName, newDescription);
        } else if (selectedCategory.equals(categoryTypes[2])) {
            creationResult = requestHandler.createLifeAreaCategory(user, newName,
                    la);
        } else {
            creationResult = "you have tried to create an unknown type!!";
        }
    }

    public String getCreationResult() {
        return creationResult;
    }

    public void setCreationResult(String creationResult) {
        this.creationResult = creationResult;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(int estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String[] getCategoryTypes() {
        return categoryTypes;
    }

    public void setCategoryTypes(String[] categoryTypes) {
        this.categoryTypes = categoryTypes;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public int getJoy() {
        return joy;
    }

    public void setJoy(int joy) {
        this.joy = joy;
    }

    @AddToObjectMapper
    public List<TaskType> getTaskTypes() {
        return taskTypes;
    }

    @AddToObjectMapper
    public List<LifeArea> getLifeAreas() {
        return lifeAreas;
    }

    @AddToObjectMapper
    public List<LifeAreaCategory> getLifeAreaCategories() {
        return lifeAreaCategories;
    }

    public void setLifeAreas(List<LifeArea> lifeAreas) {
        this.lifeAreas = lifeAreas;
    }

    public void setTaskTypes(List<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }

    public void setLifeAreaCategories(List<LifeAreaCategory> lifeAreaCategories) {
        this.lifeAreaCategories = lifeAreaCategories;
    }

}
