package com.gaviria.transaction.application.usecases;

import java.util.List;

import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.domain.repositories.TransactionRepository;

public class GetTransactionsByUserUseCase {

    private final TransactionRepository repository;

    public GetTransactionsByUserUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> execute(String usuario) {
        return repository.findByUsuario(usuario);
    }
}