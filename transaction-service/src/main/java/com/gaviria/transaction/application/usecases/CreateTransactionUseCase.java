package com.gaviria.transaction.application.usecases;

import java.time.LocalDateTime;

import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.domain.repositories.TransactionRepository;
import com.gaviria.transaction.domain.validators.TransactionValidator;


public class CreateTransactionUseCase {

    private final TransactionRepository repository;
    private final TransactionValidator validator;

    public CreateTransactionUseCase(TransactionRepository repository) {
        this.repository = repository;
        this.validator = new TransactionValidator(repository);
    }

    public Transaction execute(String usuario, int monto, String comercio, LocalDateTime fecha) {
        validator.validate(usuario, monto, fecha, true);

        Transaction tx = new Transaction(null, monto, comercio, usuario, fecha);
        return repository.save(tx);
    }
}