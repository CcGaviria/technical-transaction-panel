package com.gaviria.transaction.usecases;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.gaviria.transaction.application.usecases.CreateTransactionUseCase;
import com.gaviria.transaction.domain.exceptions.MaxTransactionsReachedException;
import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.domain.repositories.TransactionRepository;

class CreateTransactionUseCaseTest {

    @Test
    void shouldCreateTransactionIfUnderLimit() {
        TransactionRepository repository = mock(TransactionRepository.class);
        CreateTransactionUseCase useCase = new CreateTransactionUseCase(repository);

        when(repository.countByUsuario("Cristian")).thenReturn(10L);

        Transaction saved = new Transaction(1L, 1000, "Tenpo", "Cristian", LocalDateTime.now());
        when(repository.save(any())).thenReturn(saved);

        Transaction result = useCase.execute("Cristian", 1000, "Tenpo", LocalDateTime.now());

        assertThat(result.getComercio()).isEqualTo("Tenpo");
        verify(repository).save(any());
    }

    @Test
    void shouldThrowExceptionIfUserHasTooManyTransactions() {
        TransactionRepository repository = mock(TransactionRepository.class);
        CreateTransactionUseCase useCase = new CreateTransactionUseCase(repository);

        when(repository.countByUsuario("Simon")).thenReturn(100L);

        assertThatThrownBy(() ->
            useCase.execute("Simon", 500, "Tenpo", LocalDateTime.now())
        ).isInstanceOf(MaxTransactionsReachedException.class);
    }
}