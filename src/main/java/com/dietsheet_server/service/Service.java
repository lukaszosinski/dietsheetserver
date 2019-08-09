package com.dietsheet_server.service;

import com.dietsheet_server.model.User;

import java.util.List;

public interface Service<T> {

    T findById(long id);

    void save(T object);

    void update(T object);

    void deleteById(long id);

    void delete(T entity);

    List<T> findAll();

    List<T> findAll(User user);

    void deleteAll();

    boolean isExist(T object);

}
