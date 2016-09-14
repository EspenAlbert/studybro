/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author espen1
 */
@Local
public interface TaskFacadeLocal {

    public List<Task> selectTasksForLifeArea(LifeArea lifeArea);
    public List<Task> findRowsForUser(User user);
}
