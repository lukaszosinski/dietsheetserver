package com.dietsheet_server.DAO;

import com.dietsheet_server.model.User;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;

@Component("userDAO")
public class UserDAO extends AbstractDAO<User> {
    public UserDAO() {
        setClazz(User.class);
    }

    public User getByToken( String token ) {
        String hql = "from " + clazz.getName() + " c where c.token = :token";
        Query query = entityManager.createQuery(hql, clazz);
        query.setParameter("token", token);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User getByName( String username ) {
        String hql = "from " + clazz.getName() + " c where c.username = :username";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", username);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void initializeEntityChildren(User user) {
        //No children to initialize so far
    }
}
