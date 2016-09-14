package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-12T09:40:07")
@StaticMetamodel(WorkSession.class)
public class WorkSession_ { 

    public static volatile SingularAttribute<WorkSession, User> iduser;
    public static volatile SingularAttribute<WorkSession, Date> startDateAndTime;
    public static volatile SingularAttribute<WorkSession, Task> idtask;
    public static volatile SingularAttribute<WorkSession, Date> endDateAndTime;
    public static volatile SingularAttribute<WorkSession, Integer> idworkSession;
    public static volatile SingularAttribute<WorkSession, String> notes;

}