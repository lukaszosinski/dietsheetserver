package com.dietsheet_server.model.diet;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class DietEntityCascadeUpdaterImpl implements DietEntityCascadeUpdater{

    @PersistenceContext
    EntityManager entityManager;

    public void cascadeUpdateParents(DietEntity dietEntity) {
        List<DietEntity> parents = dietEntity.getParents();
        if(parents == null) {
            return;
        }
        parents.forEach(DietEntity::recalculateSummary);
        parents.forEach(parent -> entityManager.merge(parent));
        parents.forEach(parent -> cascadeUpdateParents(parent));
    }
}
