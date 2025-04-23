package com.gaviria.transaction.usecases;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.gaviria.transaction.application.usecases.UpdateTransactionUseCase;
import com.gaviria.transaction.domain.exceptions.TransactionNotFoundException;
import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.domain.repositories.TransactionRepository;

class UpdateTransactionUseCaseTest {

    @Test
    void shouldUpdateTransactionSuccessfully() {
        TransactionRepository repository = mock(TransactionRepository.class);
        UpdateTransactionUseCase useCase = new UpdateTransactionUseCase(repository);

        Transaction existing = new Transaction(1L, 500, "TenpoCl", "Simon Gaviria", LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.countByUsuario("Simon Gaviria")).thenReturn(1L);

        Transaction updated = new Transaction(1L, 1500, "Tenpo", "Simon Gaviria", existing.getFecha());
        when(repository.save(any())).thenReturn(updated);

        Transaction result = useCase.execute(1L, "Simon Gaviria", 1500, "Tenpo", existing.getFecha());

        assertThat(result.getMonto()).isEqualTo(1500);
        verify(repository).save(any());
    }

    @Test
    void shouldThrowIfTransactionNotFound() {
        TransactionRepository repository = mock(TransactionRepository.class);
        UpdateTransactionUseCase useCase = new UpdateTransactionUseCase(repository);

        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.execute(999L, "Simon Gaviria", 1500, "Tenpo", LocalDateTime.now()))
            .isInstanceOf(TransactionNotFoundException.class);
    }
}
