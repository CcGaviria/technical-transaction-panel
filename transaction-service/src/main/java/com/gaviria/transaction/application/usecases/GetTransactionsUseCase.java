package com.gaviria.transaction.application.usecases;

import java.util.List;

import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.domain.repositories.TransactionRepository;

public class GetTransactionsUseCase {

    private final TransactionRepository repository;

    public GetTransactionsUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> execute() {
        return repository.findAll();
    }
}