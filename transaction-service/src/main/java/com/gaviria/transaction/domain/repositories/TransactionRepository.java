package com.gaviria.transaction.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gaviria.transaction.domain.model.Transaction;

@Repository
public interface TransactionRepository {

    Transaction save(Transaction transaction);
    boolean existsById(Long id);
    void deleteById(Long id);
    Optional<Transaction> findById(Long id);
    List<Transaction> findByUsuario(String usuario);
    List<Transaction> findAll();
    long countByUsuario(String usuario);
}