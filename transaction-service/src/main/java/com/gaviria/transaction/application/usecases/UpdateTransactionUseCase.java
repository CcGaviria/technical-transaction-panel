package com.gaviria.transaction.application.usecases;

import java.time.LocalDateTime;
import java.util.Optional;

import com.gaviria.transaction.domain.exceptions.TransactionNotFoundException;
import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.domain.repositories.TransactionRepository;
import com.gaviria.transaction.domain.validators.TransactionValidator;

public class UpdateTransactionUseCase {

    private final TransactionRepository repository;
    private final TransactionValidator validator;

    public UpdateTransactionUseCase(TransactionRepository repository) {
        this.repository = repository;
        this.validator = new TransactionValidator(repository);
    }

    public Transaction execute(Long id, String usuario, int monto, String comercio, LocalDateTime fecha) {
        Optional<Transaction> optionalTx = repository.findById(id);
        if (optionalTx.isEmpty()) {
            throw new TransactionNotFoundException("La transacción con id " + id + " no existe.");
        }

        Transaction existing = optionalTx.get();

        boolean shouldValidateLimit = !existing.getUsuario().equals(usuario);

        validator.validate(usuario, monto, fecha, shouldValidateLimit);

        existing.setUsuario(usuario);
        existing.setMonto(monto);
        existing.setComercio(comercio);
        existing.setFecha(fecha);

        return repository.save(existing);
    }
}