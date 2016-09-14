package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.TaskType;
import com.albert.studybro.domain.entities.WorkSession;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-12T09:40:07")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Integer> iduser;
    public static volatile ListAttribute<User, WorkSession> workSessionList;
    public static volatile ListAttribute<User, TaskType> taskTypeList;
    public static volatile SingularAttribute<User, String> username;
    public static volatile SingularAttribute<User, String> email;
    public static volatile ListAttribute<User, Task> taskList;
    public static volatile SingularAttribute<User, String> password;
    public static volatile ListAttribute<User, LifeArea> lifeareaList;
    public static volatile SingularAttribute<User, String> salt;

}