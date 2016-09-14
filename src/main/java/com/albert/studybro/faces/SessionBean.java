package com.albert.studybro.faces;

import com.albert.studybro.domain.entities.User;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author espen1
 */
@Named(value = "sessionBean")
@SessionScoped
public abstract class SessionBean implements Serializable {

    protected User user;

    public SessionBean() {
        user = (User) sessionMap().get("user");
    }

    protected Map<String, Object> sessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    protected FacesContext context() {
        return FacesContext.getCurrentInstance();
    }
    protected ResourceBundle bundle = ResourceBundle.getBundle("META-INF.resources.properties.Messages",
            context().getViewRoot().getLocale());
}
