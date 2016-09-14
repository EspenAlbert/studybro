/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.albert.studybro.ejb;

import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.entities.WorkSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineGroup;
import org.primefaces.model.timeline.TimelineModel;


@Stateless
public class WorkReviewer  {

    @Inject
    RequestHandlerLocal requestHandler;

    public WorkReviewer() {
    }

    public TimelineModel getWorkSessions(Date start, Date end, User user) {
        List<Task> tasks = requestHandler.findTasksForUser(user);
        ArrayList<WorkSession> worksessions = new ArrayList<>();
        List<TimelineGroup> groups = new ArrayList<>();
        for(Task t: tasks) {
            if(t.getWorkSessionList().size() > 0) groups.add(new TimelineGroup(t.getName(), t));
            worksessions.addAll(requestHandler.findWorkSessions(t));
        }
        TimelineModel model = new TimelineModel();
        for(TimelineGroup g: groups) {
            model.addGroup(g);
        }
        for(WorkSession ws: worksessions) {
            if(ws.getStartDateAndTime().after(start) && ws.getEndDateAndTime().before(end)) {
                if(ws.getIdtask().getComplete()) model.add(new TimelineEvent(ws, ws.getStartDateAndTime(), ws.getEndDateAndTime(), true, ws.getIdtask().getName(), "taskCompleteStyle"));
                else {
                    model.add(new TimelineEvent(ws, ws.getStartDateAndTime(), ws.getEndDateAndTime(), true, ws.getIdtask().getName(), "taskIncompleteStyle"));
                }
            }
        }
        return model;
    }

    public double getHoursThisWeek(User user) {
        DateTime now = new DateTime();
        int dayOfWeek = now.getDayOfWeek();
        DateTime midnightToMonday = now.minusDays(dayOfWeek -1).minusHours(now.getHourOfDay()).minusMinutes(now.getMinuteOfHour());
        return getHoursForPeriod(user, midnightToMonday, now);
    }

    private double getHoursForPeriod(User user, DateTime start, DateTime end) {
        TimelineModel workSessionsThisWeek = getWorkSessions(start.toDate(), end.toDate(), user);
        double workHours = 0;
        for(TimelineEvent tle: workSessionsThisWeek.getEvents()) {
            DateTime startWs = new DateTime(tle.getStartDate());
            DateTime endWs = new DateTime(tle.getEndDate());
            final long millis = endWs.minus(startWs.getMillis()).getMillis();
            final double convertedToHours = (double) millis / (1000d * 3600d);
            workHours += convertedToHours;
        }
        return workHours;
    }
    public double getHoursLast7Days(User user) {
        return getHoursForPeriod(user, new DateTime().minusDays(7), new DateTime());
    }


}
