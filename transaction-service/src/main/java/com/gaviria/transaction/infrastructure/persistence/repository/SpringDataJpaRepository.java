package com.gaviria.transaction.infrastructure.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gaviria.transaction.infrastructure.persistence.entity.TransactionEntity;

public interface SpringDataJpaRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByUsuario(String usuario);
    long countByUsuario(String usuario);
}