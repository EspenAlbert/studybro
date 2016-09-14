/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.faces;

import com.albert.studybro.domain.entities.User;
import com.albert.studybro.ejb.ILoginHandlerLocal;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author espen1
 */
@Named
@RequestScoped
public class LoginBean {

    private String username;
    private String password;
    private String selectedPassword;    
    @Inject
    ILoginHandlerLocal loginHandler;


    public LoginBean() {
    }


    private String errorMessage;

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

    public String verifyLogin() {
        User success = loginHandler.verifyCredentials(username, password);
        if (success != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", success);
//            return "addTask.xhtml";
//            return "reviewWork.xhtml";
//            return "planning.xhtml";
//            return "checkInn.xhtml";
            return "index.xhtml";
        }
        return "";
    }
}
