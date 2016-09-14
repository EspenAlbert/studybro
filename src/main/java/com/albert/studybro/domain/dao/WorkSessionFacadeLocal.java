/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.entities.WorkSession;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author espen1
 */
@Local
public interface WorkSessionFacadeLocal {

    public List<WorkSession> findRowsForUser(User user);

    public List<WorkSession> findRowsForTask(Task t);
    
}
