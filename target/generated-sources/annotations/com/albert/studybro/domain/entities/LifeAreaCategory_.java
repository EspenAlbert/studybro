package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.Task;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-12T09:40:07")
@StaticMetamodel(LifeAreaCategory.class)
public class LifeAreaCategory_ { 

    public static volatile SingularAttribute<LifeAreaCategory, Integer> idlifeareaCategory;
    public static volatile SingularAttribute<LifeAreaCategory, LifeArea> idlifearea;
    public static volatile SingularAttribute<LifeAreaCategory, String> name;
    public static volatile ListAttribute<LifeAreaCategory, Task> taskList;

}