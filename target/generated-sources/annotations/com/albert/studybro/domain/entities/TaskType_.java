package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-12T09:40:07")
@StaticMetamodel(TaskType.class)
public class TaskType_ { 

    public static volatile SingularAttribute<TaskType, Integer> idtaskType;
    public static volatile SingularAttribute<TaskType, String> name;
    public static volatile ListAttribute<TaskType, Task> taskList;
    public static volatile ListAttribute<TaskType, User> userList;

}