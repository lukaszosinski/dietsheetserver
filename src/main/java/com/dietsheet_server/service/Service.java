package com.dietsheet_server.service;

import com.dietsheet_server.model.User;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;

import java.util.List;

public interface Service<T> {

    @PostAuthorize("returnObject.getOwner().getUsername() == principal.getUsername()")
    T findById(long id);

    void save(T object);

    void update(T object);

    void delete(T entity);

    List<T> findAll();

    @PostFilter("filterObject.getOwner().getUsername() == principal.username")
    List<T> findAll(List<Long> ids);

    List<T> findAll(User user);

    void deleteAll();

    boolean isExist(T object);

}
