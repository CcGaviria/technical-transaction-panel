package com.gaviria.transaction.usecases;

import com.gaviria.transaction.application.usecases.GetTransactionsByUserUseCase;
import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.domain.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class GetTransactionsByUserUseCaseTest {

    @Test
    void shouldReturnTransactionsForUser() {
        TransactionRepository repository = mock(TransactionRepository.class);
        GetTransactionsByUserUseCase useCase = new GetTransactionsByUserUseCase(repository);

        String usuario = "Cristian Gaviria";
        List<Transaction> expected = List.of(
            new Transaction(1L, 500, "Tenpo", usuario, LocalDateTime.now()),
            new Transaction(2L, 1000, "Tenpo", usuario, LocalDateTime.now())
        );

        when(repository.findByUsuario(usuario)).thenReturn(expected);

        List<Transaction> result = useCase.execute(usuario);

        assertThat(result).hasSize(2);
        verify(repository, times(1)).findByUsuario(usuario);
    }
}