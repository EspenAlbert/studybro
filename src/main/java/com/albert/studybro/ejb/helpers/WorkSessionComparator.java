package com.albert.studybro.ejb.helpers;

import com.albert.studybro.domain.entities.WorkSession;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author espen1
 */
//Not needed at the moment, but when there is more work sessions this might be useful
public class WorkSessionComparator implements Comparator<WorkSession> {

    @Override
    public int compare(WorkSession o1, WorkSession o2) {
        final Date o1Start = o1.getStartDateAndTime();
        final Date o2Start = o2.getStartDateAndTime();
        if(o1Start.before(o2Start)) return -1;
        else if(o1Start.after(o2Start)) return 1;
        return 0;
    }

}
