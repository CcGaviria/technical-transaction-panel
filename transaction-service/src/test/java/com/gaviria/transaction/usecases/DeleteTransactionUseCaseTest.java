package com.gaviria.transaction.usecases;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.gaviria.transaction.application.usecases.DeleteTransactionUseCase;
import com.gaviria.transaction.domain.exceptions.TransactionNotFoundException;
import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.domain.repositories.TransactionRepository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


class DeleteTransactionUseCaseTest {

    @Test
    void shouldDeleteTransaction() {
        TransactionRepository repository = mock(TransactionRepository.class);
        DeleteTransactionUseCase useCase = new DeleteTransactionUseCase(repository);

        Transaction tx = new Transaction(1L, 800, "Tenpo", "Cristian Gaviria", LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(tx));
        when(repository.existsById(1L)).thenReturn(true);

        useCase.execute(1L);

        verify(repository).deleteById(tx.getId());
    }

    @Test
    void shouldThrowIfTransactionNotFound() {
        TransactionRepository repository = mock(TransactionRepository.class);
        DeleteTransactionUseCase useCase = new DeleteTransactionUseCase(repository);

        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(2L))
            .isInstanceOf(TransactionNotFoundException.class);
    }
}
