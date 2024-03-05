package com.company.apps.repository;

import java.util.Optional;
import java.util.List;

public interface CSVRepository<T, K> {

    T save(T entity);

    Optional<T> findById(K id);

    List<T> findAll();

    List<T> findAll(int page, int size);

    void deleteById(K id);

    void deleteAll();
}