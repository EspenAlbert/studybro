/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.ejb;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.Task;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author espen1
 */
@Local
public interface TaskPlannerLocal {

    public List<Integer> planForLifeArea(LifeArea lifeArea);
    public List<Task> planTasks(List<Task> unsortedTasks);
}
