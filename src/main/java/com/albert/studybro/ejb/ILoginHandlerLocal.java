/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.ejb;

import com.albert.studybro.domain.entities.User;
import javax.ejb.Local;

/**
 *
 * @author espen1
 */
@Local
public interface ILoginHandlerLocal {
    
    public User verifyCredentials(String username, String password);
    
}
