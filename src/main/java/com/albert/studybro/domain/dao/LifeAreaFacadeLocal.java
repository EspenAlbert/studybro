/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author espen1
 */
@Local
public interface LifeAreaFacadeLocal {

    public LifeArea findIdForUserWithName(User user, String newName);
    public List<LifeArea> findRowsForUser(User user);
    
}
