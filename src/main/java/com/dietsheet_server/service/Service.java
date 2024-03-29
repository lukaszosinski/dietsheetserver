package com.dietsheet_server.service;

import com.dietsheet_server.model.user.User;
import java.util.List;
import java.util.Map;

public interface Service<T> {


    T findById(long id);

    void save(T object);

    void save(T object, User owner);

    void update(T object, long id);

    void delete(T entity);

    void delete(long id);

    List<T> findAll();

    List<T> findAll(List<Long> ids);

    List<T> findAll(User user, Map<String, String> params);

    void deleteAll();

    boolean isExist(T object);

}
