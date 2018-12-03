package com.wossha.social.infrastructure.repositories;

import org.springframework.stereotype.Service;

@Service
public interface Repository<T> {

    void add(T entity);

    void remove(T entity);

    void update(T entity);

}