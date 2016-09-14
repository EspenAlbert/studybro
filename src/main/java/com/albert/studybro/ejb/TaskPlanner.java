/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.ejb;

import com.albert.studybro.ejb.helpers.TupleFirstAttributeComparator;
import com.albert.studybro.ejb.helpers.Tuple;
import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.LifeAreaCategory;
import com.albert.studybro.domain.entities.Task;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;
import org.joda.time.DateTime;

/**
 *
 * @author espen1
 */
@Stateless
public class TaskPlanner implements TaskPlannerLocal {

    public static final int HOURS_PER_DAY = 2;
    //Used to reset the date after wait scores have been calculated
    private ArrayList<Task> taskWithNoDueDate;

    public TaskPlanner() {

    }

    @Override
    public List<Task> planTasks(List<Task> unsortedTasks) {
        List<Task> sortTasks = sortTasks(unsortedTasks);
        return sortTasks;
    }

    //Returns a list of ids for the tasks
    @Override
    public List<Integer> planForLifeArea(LifeArea lifeArea) {
        lifeArea.getLifeareaCategoryList().size();
        List<LifeAreaCategory> lifeareaCategoryList = lifeArea.getLifeareaCategoryList();
        List<Task> unsortedTasks = new ArrayList();
        for (LifeAreaCategory category : lifeareaCategoryList) {
            unsortedTasks.addAll(category.getTaskList());
        }
        List<Task> sortTasks = sortTasks(unsortedTasks);
        List<Integer> sortedIds = new ArrayList<>();
        for (Task t : sortTasks) {
            sortedIds.add(t.getIdtask());
        }
        return sortedIds;
    }

    private List<Task> sortTasks(List<Task> tasks) {
        if (tasks.size() < 1) {
            return new ArrayList<Task>();
        }
        List<Tuple<Double, Task>> waitScores = calculateWaitScores(tasks);
        Comparator<Tuple> comparator = new TupleFirstAttributeComparator();
        if(waitScores.size() < 1) return new ArrayList<Task>();
        PriorityQueue<Tuple> queue = new PriorityQueue<Tuple>(waitScores.size(), comparator);
        queue.addAll(waitScores);
        List<Task> sortedTasks = new ArrayList<>();
        while (queue.size() > 0) {
            sortedTasks.add((Task) queue.remove().y);
        }
        cleanUpTasksWithNoDueDates();
        return sortedTasks;
    }

    private List<Tuple<Double, Task>> calculateWaitScores(List<Task> tasks) {
        List<Tuple<Double, Task>> waitScores = new ArrayList<>();
        taskWithNoDueDate = new ArrayList<>();
        Date latestDate = null;
        for (Task task : tasks) {
            if (task.getComplete()) {
                continue;
            }
            if (task.getDueDate() != null) {
                waitScores.add(new Tuple<Double, Task>(calculateWaitScore(task), task));
                if (latestDate == null || task.getDueDate().after(latestDate)) {
                    latestDate = task.getDueDate();
                }
            } else {
                taskWithNoDueDate.add(task);
            }
        }
        DateTime dt = new DateTime(latestDate);
        dt = dt.plusDays(10);
        latestDate = dt.toDate();
        for (Task t : taskWithNoDueDate) {
            t.setDueDate(latestDate);
            waitScores.add(new Tuple<Double, Task>(calculateWaitScore(t), t));
        }
        return waitScores;
    }

    //Lower score is better
    private double calculateWaitScore(Task task) {
        Date today = new Date();
        Date taskDate = task.getDueDate();
        long diff = taskDate.getTime() - today.getTime();
        int daysFromNow = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        double importanceFactor = calculateImportanceFactor(task.getImportance(), daysFromNow);
        double workloadReduction = calculateWorkloadReduction(task.getEstimatedHours(), task.getEstimatedMinutes());
        return (daysFromNow * importanceFactor) - workloadReduction;

    }

    private double calculateImportanceFactor(Integer importance, int daysFromNow) {
        double scale = 0.5 * ((double) importance / 10);
        return daysFromNow > 0 ? (1.0d - scale) : (1.0d + scale);
    }

    private double calculateWorkloadReduction(Integer estimatedHours, Integer estimatedMinutes) {
        double workload = estimatedHours + (estimatedMinutes / 60);
        return workload / HOURS_PER_DAY;
    }

    private void cleanUpTasksWithNoDueDates() {
        for (Task t : taskWithNoDueDate) {
            t.setDueDate(null);
        }
    }
}
