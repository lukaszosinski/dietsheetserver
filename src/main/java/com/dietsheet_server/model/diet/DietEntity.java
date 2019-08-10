package com.dietsheet_server.model.diet;

import com.dietsheet_server.model.OwnedEntity;

import javax.persistence.*;

@MappedSuperclass
public abstract class DietEntity extends OwnedEntity {

    @OneToOne(cascade =
            CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @JoinColumn(
            name = "summary_id",
            referencedColumnName = "id",
            unique = true
    )
    private Summary summary;

    DietEntity() {
        super();
    }

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public void updateSummary(Summary newSummary) {
        newSummary.setId(this.getSummary().getId());
        this.setSummary(newSummary);
    }

    public abstract void recalculateSummary();
}
