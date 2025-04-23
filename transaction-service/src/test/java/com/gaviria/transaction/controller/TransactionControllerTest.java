package com.gaviria.transaction.controller;

import com.gaviria.transaction.application.config.UseCaseConfig;
import com.gaviria.transaction.application.usecases.CreateTransactionUseCase;
import com.gaviria.transaction.application.usecases.DeleteTransactionUseCase;
import com.gaviria.transaction.application.usecases.GetTransactionsByUserUseCase;
import com.gaviria.transaction.application.usecases.UpdateTransactionUseCase;
import com.gaviria.transaction.domain.model.Transaction;
import com.gaviria.transaction.infrastructure.config.SecurityConfig;
import com.gaviria.transaction.infrastructure.interceptor.RateLimitInterceptor;
import com.gaviria.transaction.interfaces.controller.TransactionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(TransactionController.class)
@Import({ UseCaseConfig.class, TransactionController.class, SecurityConfig.class })
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateTransactionUseCase createTransactionUseCase;

    @MockBean
    private UpdateTransactionUseCase updateTransactionUseCase;
    @MockBean
    private DeleteTransactionUseCase deleteTransactionUseCase;

    @MockBean
    private GetTransactionsByUserUseCase getTransactionsByUserUseCase;

    @MockBean
    private RedisTemplate<String, String> redisTemplate;

    @MockBean
    private RateLimitInterceptor rateLimitInterceptor;

    @Test
    void shouldCreateTransaction() throws Exception {
        Transaction transaction = new Transaction(1L, 1000, "Tenpo", "cristian", LocalDateTime.now());
        when(createTransactionUseCase.execute("cristian", 1000, "Tenpo", LocalDateTime.now())).thenReturn(transaction);

        mockMvc.perform(post("/transactions")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"usuario\":\"cristian\", \"monto\":1000, \"comercio\":\"Tenpo\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnTransactionsByUser() throws Exception {
        Transaction t1 = new Transaction(1L, 1000, "Tenpo", "cristian", LocalDateTime.now());
        Transaction t2 = new Transaction(2L, 500, "TenpoCl", "cristian", LocalDateTime.now());

        when(getTransactionsByUserUseCase.execute("cristian")).thenReturn(List.of(t1, t2));

        mockMvc.perform(get("/transactions/cristian"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateTransaction() throws Exception {
        Transaction updated = new Transaction(1L, 2000, "Tenpo", "cristian", LocalDateTime.now());

        when(updateTransactionUseCase.execute(1L, "cristian", 2000, "Tenpo", LocalDateTime.now())).thenReturn(updated);

        mockMvc.perform(put("/transactions")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"monto\":2000\"}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteTransaction() throws Exception {
        doNothing().when(deleteTransactionUseCase).execute(1L);

        mockMvc.perform(delete("/transactions/1").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
