package com.dietsheet_server.model.diet;

import com.dietsheet_server.model.OwnedEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class DietEntity extends OwnedEntity {
    @OneToOne(cascade =
            CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @JoinColumn(
            name = "summary_id",
            referencedColumnName = "id",
            unique = true
    )
    private Summary summary;

    public void updateSummary(Summary newSummary) {
        newSummary.setId(this.getSummary().getId());
        this.setSummary(newSummary);
    }

    public abstract void recalculateSummary();
}
