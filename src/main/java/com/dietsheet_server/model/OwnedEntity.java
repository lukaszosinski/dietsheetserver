package com.dietsheet_server.model;

import com.dietsheet_server.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
public abstract class OwnedEntity {
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;
}
