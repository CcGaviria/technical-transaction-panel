package com.gaviria.transaction.interfaces.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {
    private Long id;
    private int monto;
    private String comercio;
    private String usuario;
    private LocalDateTime fecha;
}