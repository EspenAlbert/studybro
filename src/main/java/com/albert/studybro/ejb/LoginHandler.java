package com.albert.studybro.ejb;

import com.albert.studybro.domain.dao.UserFacadeLocal;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.jpa.PersistenceUnit;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author espen1
 */
@Named
@Stateless
public class LoginHandler implements ILoginHandlerLocal {

    @PersistenceContext(unitName = PersistenceUnit.name)
    EntityManager em;

    public LoginHandler() {

    }
    @Inject
    UserFacadeLocal userDao;

    @Override
    public User verifyCredentials(String username, String password) {
        User user = userDao.find(username);
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

}
