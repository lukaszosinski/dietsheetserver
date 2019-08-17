package com.dietsheet_server.model.diet;

import com.dietsheet_server.DAO.DietEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DietEntityCascadeUpdaterImpl implements DietEntityCascadeUpdater{

    @Autowired
    DietEntityDAO dietEntityDAO;

    public void cascadeUpdateParents(DietEntity dietEntity) {
        List<DietEntity> parents = dietEntity.getParents();
        if(parents == null) {
            return;
        }
        parents.forEach(DietEntity::recalculateSummary);
        parents.forEach(parent -> dietEntityDAO.update(parent));
        parents.forEach(this::cascadeUpdateParents);
    }
}
