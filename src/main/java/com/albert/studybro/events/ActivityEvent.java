package com.albert.studybro.events;

import java.io.Serializable;
import org.joda.time.DateTime;

/**
 *
 * @author espen1
 */
public class ActivityEvent implements Serializable{

    private final String name;
    private final Object object;
    private final DateTime time;
    private final String editComponent;

    public ActivityEvent(String name, Object object, String editComponent) {
        this.name = name;
        this.object = object;
        time = new DateTime();
        this.editComponent = editComponent;
    }

    public String getName() {
        return name;
    }

    public Object getObject() {
        return object;
    }

    public DateTime getTime() {
        return time;
    }

    public String getEditComponent() {
        return editComponent;
    }
    
}
