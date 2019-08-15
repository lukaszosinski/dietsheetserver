package com.dietsheet_server.service;

import com.dietsheet_server.model.User;
import java.util.List;

public interface Service<T> {


    T findById(long id);

    void save(T object);

    void save(T object, User owner);

    void update(T object, long id);

    void delete(T entity);

    List<T> findAll();

    List<T> findAll(List<Long> ids);

    List<T> findAll(User user);

    void deleteAll();

    boolean isExist(T object);

}
