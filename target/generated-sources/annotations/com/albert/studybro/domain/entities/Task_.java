package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.LifeAreaCategory;
import com.albert.studybro.domain.entities.TaskType;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.entities.WorkSession;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-12T09:40:07")
@StaticMetamodel(Task.class)
public class Task_ { 

    public static volatile SingularAttribute<Task, User> iduser;
    public static volatile SingularAttribute<Task, LifeAreaCategory> idlifeareaCategory;
    public static volatile SingularAttribute<Task, Integer> joy;
    public static volatile SingularAttribute<Task, Integer> estimatedMinutes;
    public static volatile ListAttribute<Task, WorkSession> workSessionList;
    public static volatile SingularAttribute<Task, TaskType> idtaskType;
    public static volatile SingularAttribute<Task, Integer> idtask;
    public static volatile SingularAttribute<Task, Integer> importance;
    public static volatile SingularAttribute<Task, Boolean> complete;
    public static volatile SingularAttribute<Task, String> description;
    public static volatile SingularAttribute<Task, String> name;
    public static volatile SingularAttribute<Task, Integer> estimatedHours;
    public static volatile SingularAttribute<Task, Date> dueDate;

}