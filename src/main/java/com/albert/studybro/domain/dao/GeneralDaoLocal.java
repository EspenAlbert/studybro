/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.domain.dao;

import javax.ejb.Local;

/**
 *
 * @author espen1
 */
@Local
public interface GeneralDaoLocal {
    
    public void delete(Object object);

    public void edit(Object o);
    
    public <T extends Object> T find(Object id, Class<T> t);
    
     public void create(Object entity);
}
