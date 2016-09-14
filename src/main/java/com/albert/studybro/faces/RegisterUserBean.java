/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.faces;

import com.albert.studybro.domain.dao.UserFacadeLocal;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.ejb.RequestHandlerLocal;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author espen1
 */
@Named
@RequestScoped
public class RegisterUserBean {

    private String username;
    private String password;
    private String errorMessage;

    @Inject
    UserFacadeLocal userDao;
    @Inject
    RequestHandlerLocal requestHandler;

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void save() {
        if(userDao.find(username) != null) {
            errorMessage = "User name already exist! Try again ";
        }
        else {
            requestHandler.createUser(new User(username, password));
            errorMessage = "New user with name: " + username + " was successfuly created!!";
        }
    }
}
