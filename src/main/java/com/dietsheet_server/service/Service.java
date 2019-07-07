package com.dietsheet_server.service;

import java.util.List;

public interface Service<T> {

    T findById(long id);

    T findByName(String name);

    void save(T object);

    void update(T object);

    void deleteById(long id);

    List<T> findAll();

    void deleteAll();

    public boolean isExist(T object);
}
