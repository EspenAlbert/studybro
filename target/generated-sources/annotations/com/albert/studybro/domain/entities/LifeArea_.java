package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.LifeAreaCategory;
import com.albert.studybro.domain.entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-08-12T09:40:07")
@StaticMetamodel(LifeArea.class)
public class LifeArea_ { 

    public static volatile SingularAttribute<LifeArea, User> iduser;
    public static volatile ListAttribute<LifeArea, LifeAreaCategory> lifeareaCategoryList;
    public static volatile SingularAttribute<LifeArea, Integer> idlifearea;
    public static volatile SingularAttribute<LifeArea, String> description;
    public static volatile SingularAttribute<LifeArea, String> name;

}