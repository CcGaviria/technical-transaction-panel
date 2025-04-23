package com.gaviria.transaction.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gaviria.transaction.application.usecases.CreateTransactionUseCase;
import com.gaviria.transaction.application.usecases.DeleteTransactionUseCase;
import com.gaviria.transaction.application.usecases.GetTransactionsByUserUseCase;
import com.gaviria.transaction.application.usecases.GetTransactionsUseCase;
import com.gaviria.transaction.application.usecases.UpdateTransactionUseCase;
import com.gaviria.transaction.domain.repositories.TransactionRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateTransactionUseCase createTransactionUseCase(TransactionRepository repository) {
        return new CreateTransactionUseCase(repository);
    }

    @Bean
    public UpdateTransactionUseCase updateTransactionUseCase(TransactionRepository repository) {
        return new UpdateTransactionUseCase(repository);
    }

    @Bean
    public DeleteTransactionUseCase deleteTransactionUseCase(TransactionRepository repository) {
        return new DeleteTransactionUseCase(repository);
    }

    @Bean
    public GetTransactionsByUserUseCase getTransactionsByUserUseCase(TransactionRepository repository) {
        return new GetTransactionsByUserUseCase(repository);
    }

    @Bean
    public GetTransactionsUseCase getTransactionsUseCase(TransactionRepository repository) {
        return new GetTransactionsUseCase(repository);
    }
}