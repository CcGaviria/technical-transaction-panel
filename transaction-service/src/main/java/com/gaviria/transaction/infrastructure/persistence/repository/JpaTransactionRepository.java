package com.gaviria.transaction.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.domain.repositories.TransactionRepository;
import com.gaviria.transaction.infrastructure.persistence.entity.TransactionEntity;

@Repository
public class JpaTransactionRepository implements TransactionRepository {

    @Autowired
    private SpringDataJpaRepository jpa;

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = TransactionEntity.builder()
            .id(transaction.getId())
            .monto(transaction.getMonto())
            .comercio(transaction.getComercio())
            .usuario(transaction.getUsuario())
            .fecha(transaction.getFecha())
            .build();

        TransactionEntity saved = jpa.save(entity);

        return new Transaction(
            saved.getId(),
            saved.getMonto(),
            saved.getComercio(),
            saved.getUsuario(),
            saved.getFecha()
        );
    }

    @Override
    public boolean existsById(Long id) {
        return jpa.existsById(id);
    }
    
    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return jpa.findById(id).map(e -> new Transaction(e.getId(), e.getMonto(), e.getComercio(), e.getUsuario(), e.getFecha()));
    }

    @Override
    public List<Transaction> findByUsuario(String usuario) {
        return jpa.findByUsuario(usuario).stream()
            .map(e -> new Transaction(e.getId(), e.getMonto(), e.getComercio(), e.getUsuario(), e.getFecha()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findAll() {
        return jpa.findAll().stream()
            .map(e -> new Transaction(e.getId(), e.getMonto(), e.getComercio(), e.getUsuario(), e.getFecha()))
            .collect(Collectors.toList());
    }

    @Override
    public long countByUsuario(String usuario) {
        return jpa.countByUsuario(usuario);
    }


}