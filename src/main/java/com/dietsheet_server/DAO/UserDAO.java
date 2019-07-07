package com.dietsheet_server.DAO;

import com.dietsheet_server.model.User;
import org.springframework.stereotype.Component;

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
        return (User) query.getSingleResult();
    }
}
