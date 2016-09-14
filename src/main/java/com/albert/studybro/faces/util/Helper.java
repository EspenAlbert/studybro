package com.albert.studybro.faces.util;

import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author espen1
 */
public class Helper {

    public static Map<String, Object> sessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public static ResourceBundle bundle() {
        return ResourceBundle.getBundle("META-INF.resources.properties.Messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }
    public static String getResourceName(String name) {
        return bundle().getString(name);
    }
}
