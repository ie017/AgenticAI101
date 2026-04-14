package com.example.balance.repository;

import com.example.balance.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory account repository implementation using a concurrent map.
 */
@Repository
public class InMemoryAccountRepository implements GenericRepository<Account, String> {

    private final ConcurrentHashMap<String, Account> storage = new ConcurrentHashMap<>();

    /**
     * Saves account into memory.
     *
     * @param entity account to save
     */
    @Override
    public void save(Account entity) {
        storage.put(entity.accountId(), entity);
    }

    /**
     * Finds account by id.
     *
     * @param id account id
     * @return optional account
     */
    @Override
    public Optional<Account> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Returns all accounts.
     *
     * @return account list
     */
    @Override
    public List<Account> findAll() {
        return List.copyOf(storage.values());
    }

    /**
     * Deletes account by id.
     *
     * @param id account id
     */
    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }
}

