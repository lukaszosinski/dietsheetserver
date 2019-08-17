package com.dietsheet_server.DAO;

import com.dietsheet_server.model.diet.DietEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class DietEntityDAO {

    @PersistenceContext
    EntityManager entityManager;

    public void update(DietEntity dietEntity) {
        entityManager.merge(dietEntity);
    }
}
