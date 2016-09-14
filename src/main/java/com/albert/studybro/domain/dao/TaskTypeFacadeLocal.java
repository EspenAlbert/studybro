/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.TaskType;
import com.albert.studybro.domain.entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author espen1
 */
@Local
public interface TaskTypeFacadeLocal {

    public TaskType findForUserWithName(User user, String newName);
}
