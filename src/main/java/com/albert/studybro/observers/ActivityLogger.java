package com.albert.studybro.observers;

import com.albert.studybro.events.ActivityEvent;
import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author espen1
 */
@Named
@SessionScoped
public class ActivityLogger implements Serializable {

    private ArrayList<ActivityEvent> events;

    public ActivityLogger() {
        events = new ArrayList<ActivityEvent>();
    }

    public void listenForActivities(@Observes ActivityEvent event) {
        events.add(event);
        RequestContext.getCurrentInstance().update("sidePanelForm:activities");
    }

    public void removeActitivtyEvent(ActivityEvent event) {
        events.remove(event);
        RequestContext.getCurrentInstance().update("sidePanelForm:activities");
    }

    public ArrayList<ActivityEvent> getEvents() {
        return events;
    }

}
