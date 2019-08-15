package com.dietsheet_server.model.diet;

public interface DietEntityCascadeUpdater {
    public void cascadeUpdateParents(DietEntity dietEntity);
}
