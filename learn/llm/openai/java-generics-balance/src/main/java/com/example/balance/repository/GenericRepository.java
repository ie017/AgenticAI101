package com.example.balance.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository abstraction for CRUD-like operations.
 *
 * @param <T> entity type
 * @param <ID> identifier type
 */
public interface GenericRepository<T, ID> {

    /**
     * Saves an entity.
     *
     * @param entity entity instance
     */
    void save(T entity);

    /**
     * Finds entity by id.
     *
     * @param id entity id
     * @return optional entity
     */
    Optional<T> findById(ID id);

    /**
     * Returns all entities.
     *
     * @return immutable list of entities
     */
    List<T> findAll();

    /**
     * Deletes entity by id.
     *
     * @param id entity id
     */
    void deleteById(ID id);
}

