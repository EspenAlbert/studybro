package com.albert.studybro.domain.dao;

import com.albert.studybro.domain.entities.LifeArea;
import com.albert.studybro.domain.entities.LifeArea_;
import com.albert.studybro.domain.entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

/**
 *
 * @author espen1
 */
@Stateless
public class LifeAreaFacade extends TableWithUser<LifeArea> implements LifeAreaFacadeLocal {


    public LifeAreaFacade() {
        super(LifeArea.class);
    }

    @Override
    protected Path<User> getUserColumn(Root<LifeArea> root) {
        return root.get(LifeArea_.iduser);
    }

    @Override
    protected Path<String> getNameColumnPath(Root<LifeArea> root) {
        return root.get(LifeArea_.name);
    }

    @Override
    protected Path<Integer> getIdPath(Root<LifeArea> root) {
        return root.get(LifeArea_.idlifearea);
    }

}
