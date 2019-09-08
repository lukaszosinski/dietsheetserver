package com.dietsheet_server.DAO;

import com.dietsheet_server.model.user.User;
import org.hibernate.Hibernate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Component("userDAO")
public class UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    public User get(long id) {
        User user = entityManager.find(User.class, id);
        if(user == null) {
            throw new ResourceNotFoundException();
        }
        Hibernate.initialize(user.getData());
        Hibernate.initialize(user.getDietLimits());
        Hibernate.initialize(user.getPreferences());
        return user;
    }

    public void save(User user) {
        entityManager.persist(user);
    }

    public void update(User user) {
        entityManager.merge(user);
    }

    public User getByToken( String token ) {
        String hql = "from " + User.class.getName() + " c where c.token = :token";
        Query query = entityManager.createQuery(hql, User.class);
        query.setParameter("token", token);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User getByName( String username ) {
        String hql = "from " + User.class.getName() + " c where c.username = :username";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", username);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
