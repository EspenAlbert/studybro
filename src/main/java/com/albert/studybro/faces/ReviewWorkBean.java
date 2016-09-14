package com.albert.studybro.faces;

import com.albert.studybro.faces.util.ObjectEditor;
import com.albert.studybro.domain.entities.WorkSession;
import com.albert.studybro.ejb.WorkReviewer;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

/**
 *
 * @author espen1
 */
@Named
@ViewScoped
public class ReviewWorkBean extends SessionBean implements Serializable {  
   
    private TimelineModel model;  
   
    private String hoursLast7Days;
    private String workHoursThisWeek;
    
    private Date start;
    private Date end;
   
    @Inject
    WorkReviewer modelCreator;
    @Inject
    ObjectEditor objectEditor;
    
    @PostConstruct 
    protected void initialize() {  
        Calendar cal = Calendar.getInstance();
        cal.set(2014, Calendar.JUNE, 12, 0, 0, 0);  
        Date start = cal.getTime();
        cal.set(2017, Calendar.JUNE, 12, 0, 0, 0);
        Date end = cal.getTime();
        model = modelCreator.getWorkSessions(start, end, user);
        workHoursThisWeek = String.format("%.2f" , modelCreator.getHoursThisWeek(user));
        hoursLast7Days = String.format("%.2f" , modelCreator.getHoursLast7Days(user));
    }  
   
    public String datesSelected() {
        model = modelCreator.getWorkSessions(start, end, user);
        return "";
    }
    
    public void onSelect(TimelineSelectEvent e) {  
        TimelineEvent timelineEvent = e.getTimelineEvent();  
        WorkSession workSession = (WorkSession) timelineEvent.getData();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected worksession notes:", workSession.getNotes());  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        objectEditor.createEditComponentPage(workSession, "workSessionEditor.xhtml");
    }  
   
    public TimelineModel getModel() {  
        return model;  
    }  

    public String getHoursLast7Days() {
        return hoursLast7Days;
    }

    public String getWorkHoursThisWeek() {
        return workHoursThisWeek;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }



    
}  

