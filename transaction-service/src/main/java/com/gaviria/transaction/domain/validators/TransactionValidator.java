package com.gaviria.transaction.domain.validators;

import java.time.LocalDateTime;

import com.gaviria.transaction.domain.exceptions.InvalidTransactionException;
import com.gaviria.transaction.domain.exceptions.MaxTransactionsReachedException;
import com.gaviria.transaction.domain.repositories.TransactionRepository;


public class TransactionValidator {

    private static final int MAX_TRANSACTIONS = 100;
    private final TransactionRepository repository;

    public TransactionValidator(TransactionRepository repository) {
        this.repository = repository;
    }

    public void validate(String usuario, int monto, LocalDateTime fecha, boolean validateLimit) {
        if (monto <= 0) {
            throw new InvalidTransactionException("El monto debe ser mayor que cero.");
        }

        if (fecha.isAfter(LocalDateTime.now())) {
            throw new InvalidTransactionException("La fecha no puede estar en el futuro.");
        }

        if (validateLimit) {
            long count = repository.countByUsuario(usuario);
            if (count >= MAX_TRANSACTIONS) {
                throw new MaxTransactionsReachedException(
                    String.format("El usuario ya tiene %d transacciones.", MAX_TRANSACTIONS)
                );
            }
        }
    }
}