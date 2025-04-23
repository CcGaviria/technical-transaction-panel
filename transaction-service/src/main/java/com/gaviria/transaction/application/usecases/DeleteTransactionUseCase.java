package com.gaviria.transaction.application.usecases;

import com.gaviria.transaction.domain.exceptions.TransactionNotFoundException;
import com.gaviria.transaction.domain.repositories.TransactionRepository;

public class DeleteTransactionUseCase {
    private final TransactionRepository repository;

    public DeleteTransactionUseCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public void execute(Long transactionId) {
        if (!repository.existsById(transactionId)) {
            throw new TransactionNotFoundException("La transacción con el ID " + transactionId + " no existe.");
        }

        repository.deleteById(transactionId);
    }
}